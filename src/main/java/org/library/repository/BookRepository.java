package org.library.repository;

import org.library.model.Book;

import java.io.IOException;
import java.util.List;

public interface BookRepository {
    List<Book> loadBooks() throws IOException;
    void saveBooks(List<Book> books) throws IOException;
}
