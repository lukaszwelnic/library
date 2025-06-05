package org.library.repository.jdbc;

import org.library.model.Author;
import org.library.model.Book;
import org.library.model.Genre;
import org.library.repository.BookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> mapRowToBook(rs);

    private Book mapRowToBook(ResultSet rs) throws SQLException {
        Author author = new Author(
                rs.getInt("author_id"),
                rs.getString("author_name")
        );
        Genre genre = new Genre(
                rs.getInt("genre_id"),
                rs.getString("genre_name")
        );
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                author,
                genre,
                rs.getString("description")
        );
    }

    @Override
    public List<Book> read() {
        String sql = """
            SELECT b.id, b.title,
                   a.id AS author_id, a.name AS author_name,
                   g.id AS genre_id, g.name AS genre_name,
                   b.description
            FROM books b
            JOIN authors a ON b.author_id = a.id
            JOIN genres g ON b.genre_id = g.id
            ORDER BY b.id
        """;
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, author_id, genre_id, description) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getGenre().getId());
            ps.setString(4, book.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            book.setId(key.intValue());
        }

        return book;
    }

    @Override
    public void update(int id, Book book) {
        String sql = "UPDATE books SET title = ?, author_id = ?, genre_id = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId(),
                book.getDescription(),
                id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Book> findById(int id) {
        String sql = """
            SELECT b.id, b.title,
                   a.id AS author_id, a.name AS author_name,
                   g.id AS genre_id, g.name AS genre_name,
                   b.description
            FROM books b
            JOIN authors a ON b.author_id = a.id
            JOIN genres g ON b.genre_id = g.id
            WHERE b.id = ?
        """;
        return jdbcTemplate.query(sql, bookRowMapper, id).stream().findFirst();
    }
}
