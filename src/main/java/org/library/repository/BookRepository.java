package org.library.repository;

import com.fasterxml.jackson.databind.SequenceWriter;
import org.library.mapper.BookMapper;
import org.library.model.Book;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private static final String FILE_PATH = "src/main/resources/books.csv";
    private final BookMapper bookMapper;

    public BookRepository(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<Book> loadBooks() throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>(); // File missing is not an error, just an empty list
        }

        try {
            return bookMapper.map(file);
        } catch (IOException e) { // Catch exception from BookMapper
            throw new IOException("❌  Error loading books from file: " + FILE_PATH, e);
        }
    }

    public void saveBooks(List<Book> books) throws IOException {
        File file = new File(FILE_PATH);
        try (SequenceWriter writer = bookMapper.map(books).writeValues(file)) {
            writer.writeAll(books);
        } catch (IOException e) {
            throw new IOException("❌  Error saving books to file: " + FILE_PATH, e);
        }
    }
}
