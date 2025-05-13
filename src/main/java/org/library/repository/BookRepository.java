package org.library.repository;

import org.library.model.Book;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository {
    List<Book> read() throws IOException;
    void create(List<Book> books) throws IOException; // Only used in CSV
    void update(List<Book> books) throws IOException; // Only used in CSV
    void delete(List<Book> books) throws IOException; // Only used in CSV
    Optional<Book> findById(int id) throws IOException;
//    void create(Book book, int authorId, int genreId);
//    void update(Book book, int authorId, int genreId);
//    void delete(int id);
}
