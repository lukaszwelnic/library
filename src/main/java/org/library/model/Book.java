package org.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String description;

    public Book() {}

    public Book(Integer id, String title, Author author, Genre genre, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }
    public Genre getGenre() { return genre; }
    public String getDescription() { return description; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(Author author) { this.author = author; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return String.format("%-5s %-60s %-40s %-40s %s", id, title, author.getName(), genre.getName(), description);
    }
}
