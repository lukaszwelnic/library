package org.library.service;

import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getBooks() throws IOException {
        return List.copyOf(repository.loadBooks());
    }

    public void addBook(Book book) throws IOException {
        List<Book> books = repository.loadBooks();
        if (books.stream().anyMatch(b -> b.getId() == book.getId())) {
            throw new IllegalArgumentException("❌  Book ID " + book.getId() + " already exists.");
        }
        books.add(book);
        repository.saveBooks(books);
    }

    public void updateBook(int id, Book updatedBook) throws IOException {
        List<Book> books = repository.loadBooks();
        Optional<Book> bookOptional = books.stream().filter(b -> b.getId() == id).findFirst();

        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("❌  Cannot update. Book ID " + id + " not found.");
        }
        books.replaceAll(book -> book.getId() == id ? updatedBook : book);
        repository.saveBooks(books);
    }

    public void deleteBook(int id) throws IOException {
        List<Book> books = repository.loadBooks();
        if (books.removeIf(book -> book.getId() == id)) {
            repository.saveBooks(books);
        } else {
            throw new IllegalArgumentException("❌  Cannot delete. Book ID " + id + " not found.");
        }
    }
}
