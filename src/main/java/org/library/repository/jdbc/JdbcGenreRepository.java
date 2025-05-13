package org.library.repository.jdbc;

import org.library.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreRepository {
    private final JdbcTemplate jdbc;

    public JdbcGenreRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Genre> findAll() {
        return jdbc.query("SELECT id, name FROM genres ORDER BY id",
                (rs, rowNum) -> new Genre(rs.getInt("id"), rs.getString("name")));
    }

    public int findIdByName(String name) {
        return jdbc.queryForObject("SELECT id FROM genres WHERE name = ?", Integer.class, name);
    }

    public void insert(String name) {
        jdbc.update("INSERT INTO genres(name) VALUES (?)", name);
    }
}
