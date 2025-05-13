package org.library.service;

import org.library.model.Author;
import org.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // CRUD Operations
    public List<Author> fetchAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> fetchAuthorById(int id) {
        return authorRepository.findById(id);
    }

    public void createAuthor(Author author) {
        authorRepository.addAuthor(author);
    }

    public void updateAuthor(int id, Author author) {
        authorRepository.updateAuthor(id, author);
    }

    public void deleteAuthor(int id) {
        authorRepository.deleteAuthor(id);
    }
}
