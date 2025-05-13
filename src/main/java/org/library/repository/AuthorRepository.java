package org.library.repository;

import org.library.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";
        jdbcTemplate.update(sql, author.getName());
    }

    public void updateAuthor(int id, Author author) {
        String sql = "UPDATE authors SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getName(), id);
    }

    public void deleteAuthor(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Author> findAll() {
        String sql = "SELECT id, name FROM authors";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Author(rs.getInt("id"), rs.getString("name")));
    }

    public Optional<Author> findById(int id) {
        String sql = "SELECT id, name FROM authors WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> new Author(rs.getInt("id"), rs.getString("name")))
                .stream().findFirst();
    }
}
