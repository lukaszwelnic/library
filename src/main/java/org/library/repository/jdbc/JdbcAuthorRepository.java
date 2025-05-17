package org.library.repository.jdbc;

import org.library.model.Author;
import org.library.repository.AuthorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcAuthorRepository implements AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> mapRowToAuthor(rs);

    private Author mapRowToAuthor(ResultSet rs) throws SQLException {
        return new Author(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    @Override
    public List<Author> read() {
        String sql = "SELECT id, name FROM authors ORDER BY id";
        return jdbcTemplate.query(sql, authorRowMapper);
    }

    @Override
    public void create(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";
        jdbcTemplate.update(sql, author.getName());
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE authors SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getName(), author.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Author> findById(int id) {
        String sql = "SELECT id, name FROM authors WHERE id = ?";
        List<Author> authors = jdbcTemplate.query(sql, authorRowMapper, id);
        if (authors.isEmpty()) return Optional.empty();
        return Optional.of(authors.get(0));
    }
}
