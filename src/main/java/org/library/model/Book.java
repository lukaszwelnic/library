package org.library.model;

public class Book {
    private int id;
    private String title;
    private String description;
    private Author author;
    private Genre genre;

    public Book() {}

    public Book(int id, String title, String description, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Author getAuthor() { return author; }
    public Genre getGenre() { return genre; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setAuthor(Author author) { this.author = author; }
    public void setGenre(Genre genre) { this.genre = genre; }

    @Override
    public String toString() {
        return String.format("%-5d %-40s %-20s %-20s %s", id, title, author.getName(), genre.getName(), description);
    }
}
