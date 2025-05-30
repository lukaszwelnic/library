package org.library.repository;

import org.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> read();
    Book create(Book book);
    void update(int id, Book book);
    void delete(int id);
    Optional<Book> findById(int id);
}
