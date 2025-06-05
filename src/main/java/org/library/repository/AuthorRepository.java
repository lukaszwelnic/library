package org.library.repository;

import org.library.model.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> read();
    void create(Author author);
    void update(Author author);
    void delete(int id);
    Optional<Author> findById(int id);
}
