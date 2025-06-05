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

    public List<Author> findAll() {
        return authorRepository.read();
    }

    public Optional<Author> findById(int id) {
        return authorRepository.findById(id);
    }

    public void addAuthor(Author author) {
        authorRepository.create(author);
    }

    public void updateAuthor(Author author) {
        authorRepository.update(author);
    }

    public void deleteAuthor(int id) {
        authorRepository.delete(id);
    }
}
