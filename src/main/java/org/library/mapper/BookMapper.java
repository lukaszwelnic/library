package org.library.mapper;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.library.model.Book;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class BookMapper {

    private final CsvSchema csvschema;
    private final CsvMapper csvmapper;

    public BookMapper(CsvSchema csvschema, CsvMapper csvmapper) {
        this.csvschema = csvschema;
        this.csvmapper = csvmapper;
    }

    public List<Book> map(File file) throws IOException {
        try (MappingIterator<Book> bookIterator = csvmapper.readerFor(Book.class)
                .with(csvschema)
                .readValues(file)) {
            return bookIterator.readAll();
        } catch (IOException e) {
            throw new IOException("❌  Error reading CSV file: " + file.getAbsolutePath(), e);
        }
    }

    public ObjectWriter map(List<Book> books){
        return csvmapper.writerFor(Book.class).with(csvschema);
    }
}
