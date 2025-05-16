package org.library.repository.jdbc;

import org.library.model.Genre;
import org.library.repository.GenreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcGenreRepository implements GenreRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Genre> genreRowMapper = (rs, rowNum) -> mapRowToGenre(rs);

    private Genre mapRowToGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    @Override
    public List<Genre> read() {
        String sql = "SELECT id, name FROM genres ORDER BY name";
        return jdbcTemplate.query(sql, genreRowMapper);
    }

    @Override
    public void create(Genre genre) {
        String sql = "INSERT INTO genres (name) VALUES (?)";
        jdbcTemplate.update(sql, genre.getName());
    }

    @Override
    public void update(Genre genre) {
        String sql = "UPDATE genres SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM genres WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Genre> findById(int id) {
        String sql = "SELECT id, name FROM genres WHERE id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, genreRowMapper, id);
        if (genres.isEmpty()) return Optional.empty();
        return Optional.of(genres.get(0));
    }
}
