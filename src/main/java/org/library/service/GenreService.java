package org.library.service;

import org.library.model.Genre;
import org.library.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.read();
    }

    public Optional<Genre> findById(int id) {
        return genreRepository.findById(id);
    }

    public void addGenre(Genre genre) {
        genreRepository.create(genre);
    }

    public void updateGenre(Genre genre) {
        genreRepository.update(genre);
    }

    public void deleteGenre(int id) {
        genreRepository.delete(id);
    }
}
