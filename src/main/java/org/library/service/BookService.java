package org.library.service;

import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.stereotype.Service;

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

    public List<Book> fetchAllBooks() {
        return repository.read();
    }

    public void addNewBook(Book book) {
        Optional<Book> existing = repository.findById(book.getId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("❌  " + messageService.get("error.duplicate.id", book.getId()));
        }
        repository.create(book);
    }

    public void modifyBookById(int id, Book updatedBook) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("❌  " + messageService.get("error.update.notfound", id));
        }
        repository.update(id, updatedBook);
    }

    public void removeBookById(int id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("❌  " + messageService.get("error.delete.notfound", id));
        }
        repository.delete(id);
    }
}
