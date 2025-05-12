package org.library.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.library.model.Book;
import org.library.service.MessageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("csv")
public class CsvBookRepository implements BookRepository {
    private static final String FILE_PATH = "src/main/resources/books.csv";

    private final CsvMapper csvMapper;
    private final CsvSchema csvSchema;
    private final MessageService messageService;

    public CsvBookRepository(CsvMapper csvMapper, CsvSchema csvSchema, MessageService messageService) {
        this.csvMapper = csvMapper;
        this.csvSchema = csvSchema;
        this.messageService = messageService;
    }

    @Override
    public List<Book> loadBooks() throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (MappingIterator<Book> iterator = csvMapper.readerFor(Book.class)
                .with(csvSchema)
                .readValues(file)) {
            return iterator.readAll();
        } catch (IOException e) {
            throw new IOException("❌  " + messageService.get("error.reading.csv", file.getAbsolutePath(), e.getMessage()), e);
        }
    }

    @Override
    public void saveBooks(List<Book> books) throws IOException {
        File file = new File(FILE_PATH);
        ObjectWriter writer = csvMapper.writerFor(Book.class).with(csvSchema);

        try (var sequenceWriter = writer.writeValues(file)) {
            sequenceWriter.writeAll(books);
        } catch (IOException e) {
            throw new IOException("❌  " + messageService.get("error.saving.books", FILE_PATH), e);
        }
    }
}
