package org.library.repository.jdbc;

import org.library.model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAuthorRepository {
    private final JdbcTemplate jdbc;

    public JdbcAuthorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Author> findAll() {
        return jdbc.query("SELECT id, name FROM authors ORDER BY id",
                (rs, rowNum) -> new Author(rs.getInt("id"), rs.getString("name")));
    }

    public int findIdByName(String name) {
        return jdbc.queryForObject("SELECT id FROM authors WHERE name = ?", Integer.class, name);
    }

    public void insert(String name) {
        jdbc.update("INSERT INTO authors(name) VALUES (?)", name);
    }
}
