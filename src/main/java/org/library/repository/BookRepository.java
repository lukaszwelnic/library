package org.library.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.library.model.Book;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class BookRepository {

    private static final String FILE_PATH = "src/main/resources/books.csv";
    private final CsvMapper csvMapper = new CsvMapper();
    private final CsvSchema schema = CsvSchema.builder()
            .addColumn("id")
            .addColumn("title")
            .addColumn("author")
            .addColumn("description")
            .build()
            .withHeader()
            .withColumnSeparator(';');

    private List<Book> booksCache = null;

    public List<Book> loadBooks() throws IOException {
        if (booksCache == null) {
            File file = new File(FILE_PATH);
            if (!file.exists()) return List.of();

            try (MappingIterator<Book> bookIterator = csvMapper.readerFor(Book.class)
                    .with(schema)
                    .readValues(file)) {

                booksCache = bookIterator.readAll();  // Store the loaded books in cache
            }
        }
        return booksCache;
    }

    public void saveBooks(List<Book> books) throws IOException {
        booksCache = books;  // Update the cache when saving
        try (SequenceWriter writer = csvMapper.writerFor(Book.class)
                .with(schema)
                .writeValues(new File(FILE_PATH))) {

            writer.writeAll(books);
        }
    }
}
