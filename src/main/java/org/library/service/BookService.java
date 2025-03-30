package org.library.service;

import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getBooks() throws IOException {
        return repository.loadBooks();
    }

    public void addBook(Book book) throws IOException {
        List<Book> books = repository.loadBooks();
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("❌ A book with ID " + book.getId() + " already exists.");
        }
        books.add(book);
        repository.saveBooks(books);
    }

    public void updateBook(int id, Book updatedBook) throws IOException {
        List<Book> books = repository.loadBooks();
        boolean bookExists = books.stream().anyMatch(book -> book.getId() == id);
        if (!bookExists) {
            throw new IllegalArgumentException("❌ Cannot update. Book with ID " + id + " not found.");
        }
        books.replaceAll(book -> book.getId() == id ? updatedBook : book);
        repository.saveBooks(books);
    }

    public void deleteBook(int id) throws IOException {
        List<Book> books = repository.loadBooks();
        boolean removed = books.removeIf(book -> book.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("❌ Cannot delete. Book with ID " + id + " not found.");
        }
        repository.saveBooks(books);
    }

}
