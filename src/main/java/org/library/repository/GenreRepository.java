package org.library.repository;

import org.library.model.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> read();
    void create(Genre genre);
    void update(Genre genre);
    void delete(int id);
    Optional<Genre> findById(int id);
}