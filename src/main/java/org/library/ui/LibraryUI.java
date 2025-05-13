package org.library.ui;

import org.library.model.Book;
import org.library.model.Author;
import org.library.model.Genre;
import org.library.service.AuthorService;
import org.library.service.BookService;
import org.library.service.GenreService;
import org.library.service.MessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class LibraryUI {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final MessageService messageService;
    private final Scanner scanner = new Scanner(System.in);

    public LibraryUI(BookService bookService, AuthorService authorService, GenreService genreService, MessageService messageService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.messageService = messageService;
    }

    public void start() {
        while (true) {
            System.out.println("\n" + messageService.get("menu.header"));
            System.out.println("1. " + messageService.get("menu.display"));
            System.out.println("2. " + messageService.get("menu.create"));
            System.out.println("3. " + messageService.get("menu.edit"));
            System.out.println("4. " + messageService.get("menu.delete"));
            System.out.println("5. " + messageService.get("menu.exit"));

            int choice = getInputInt("\n" + messageService.get("prompt.choice"));
            try {
                switch (choice) {
                    case 1 -> displayBooks();
                    case 2 -> createBook();
                    case 3 -> editBook();
                    case 4 -> deleteBook();
                    case 5 -> {
                        System.out.println(messageService.get("exit.message"));
                        scanner.close();
                        return;
                    }
                    default -> System.out.println(messageService.get("error.invalid.choice"));
                }
            } catch (IOException e) {
                System.err.println(messageService.get("error.generic", e.getMessage()));
                scanner.close();
            }
        }
    }

    private void displayAuthors() {
        List<Author> authors = authorService.fetchAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.authors"));
        } else {
            System.out.println("\n" + messageService.get("author.list.header"));
            authors.forEach(author -> System.out.println(author.getId() + ": " + author.getName()));
        }
    }

    private void displayGenres() {
        List<Genre> genres = genreService.fetchAllGenres();
        if (genres.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.genres"));
        } else {
            System.out.println("\n" + messageService.get("genre.list.header"));
            genres.forEach(genre -> System.out.println(genre.getId() + ": " + genre.getName()));
        }
    }

    private void displayBooks() throws IOException {
        List<Book> books = bookService.fetchAllBooks();
        if (books.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.library"));
        } else {
            System.out.printf("\n%-5s %-40s %-40s %-40s %s\n\n",
                    messageService.get("book.id"), messageService.get("book.title"), messageService.get("book.author"),
                    messageService.get("book.genre"), messageService.get("book.description"));
            books.forEach(System.out::println);
            System.out.println("\n✅  " + messageService.get("info.displayed.books", books.size()));
        }
    }

    private void createBook() throws IOException {
        try {
            List<Book> books = bookService.fetchAllBooks();
            int newId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;

            String title = getInputString(messageService.get("prompt.new.title"));
            Author author = promptForAuthor();
            Genre genre = promptForGenre();
            String description = getInputString(messageService.get("prompt.new.description"));
            Book book = new Book(newId, title, description, author, genre);
            bookService.addNewBook(book);

            System.out.println("\n✅  " + messageService.get("info.book.added",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getDescription()));
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + messageService.get("error.add.book") + "\n%s\n", e.getMessage());
        }
    }

    private void editBook() throws IOException {
        int id = getInputInt("\n" + messageService.get("prompt.edit.id"));
        try {
            List<Book> books = bookService.fetchAllBooks();
            Book bookToEdit = books.stream()
                    .filter(b -> b.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(messageService.get("error.book.not.found")));

            // Display current book details
            System.out.println("\n" + messageService.get("book.details.header"));
            System.out.println(bookToEdit);

            // Edit title
            String title = getInputString(messageService.get("prompt.edit.title"));
            if (!title.isEmpty()) {
                bookToEdit.setTitle(title);
            }

            // Edit author
            Author author = promptForAuthor(); // Get author through separate method
            if (author != null) {
                bookToEdit.setAuthor(author);
            }

            // Edit genre
            Genre genre = promptForGenre(); // Get genre through separate method
            if (genre != null) {
                bookToEdit.setGenre(genre);
            }

            // Edit description
            String description = getInputString(messageService.get("prompt.edit.description"));
            if (!description.isEmpty()) {
                bookToEdit.setDescription(description);
            }

            // Save updated book
            bookService.modifyBookById(id, bookToEdit);
            System.out.println("\n✅  " + messageService.get("info.book.updated",
                    bookToEdit.getId(), bookToEdit.getTitle(), bookToEdit.getAuthor().getName(), bookToEdit.getDescription()));
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + messageService.get("error.update.book") + " %d\n%s\n", id, e.getMessage());
        }
    }

    private void deleteBook() throws IOException {
        int id = getInputInt("\n" + messageService.get("prompt.delete.id"));
        try {
            Book deletedBook = bookService.fetchAllBooks().stream()
                    .filter(book -> book.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(messageService.get("error.book.not.found")));

            bookService.removeBookById(id);
            System.out.println("\n✅  " + messageService.get("info.book.deleted",
                    deletedBook.getId(), deletedBook.getTitle(), deletedBook.getAuthor().getName(), deletedBook.getDescription()));
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + messageService.get("error.delete.book") + " %d\n%s\n", id, e.getMessage());
        }
    }

    private Author promptForAuthor() {
        displayAuthors();
        int authorId = getInputInt(messageService.get("prompt.new.author"));
        return authorService.fetchAuthorById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid author selection"));
    }

    private Genre promptForGenre() {
        displayGenres();
        int genreId = getInputInt(messageService.get("prompt.new.genre"));
        return genreService.fetchGenreById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre selection"));
    }

    private int getInputInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print(messageService.get("error.invalid.input"));
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private String getInputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
