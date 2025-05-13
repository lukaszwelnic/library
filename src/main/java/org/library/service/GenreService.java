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

    // CRUD Operations
    public List<Genre> fetchAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> fetchGenreById(int id) {
        return genreRepository.findById(id);
    }

    public void createGenre(Genre genre) {
        genreRepository.addGenre(genre);
    }

    public void updateGenre(int id, Genre genre) {
        genreRepository.updateGenre(id, genre);
    }

    public void deleteGenre(int id) {
        genreRepository.deleteGenre(id);
    }
}
