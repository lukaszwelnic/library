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
    private final MessageService messageService;

    public BookService(BookRepository repository, MessageService messageService) {
        this.repository = repository;
        this.messageService = messageService;
    }

    public List<Book> fetchAllBooks() throws IOException {
        return List.copyOf(repository.read());
    }

    public void addNewBook(Book book) throws IOException {
        List<Book> books = repository.read();
        boolean bookExists = books.stream().anyMatch(b -> b.getId() == book.getId());
        if (bookExists) {
            throw new IllegalArgumentException("❌  " + messageService.get("error.duplicate.id", book.getId()));
        }
        books.add(book);
        repository.create(books);
    }

    public void modifyBookById(int id, Book updatedBook) throws IOException {
        List<Book> books = repository.read();
        Optional<Book> bookOptional = books.stream().filter(b -> b.getId() == id).findFirst();

        if (bookOptional.isEmpty()) {
            throw new IllegalArgumentException("❌  " + messageService.get("error.update.notfound", id));
        }
        books.replaceAll(book -> book.getId() == id ? updatedBook : book);
        repository.update(books);
    }

    public void removeBookById(int id) throws IOException {
        List<Book> books = repository.read();
        if (books.removeIf(book -> book.getId() == id)) {
            repository.delete(books);
        } else {
            throw new IllegalArgumentException("❌  " + messageService.get("error.delete.notfound", id));
        }
    }
}
