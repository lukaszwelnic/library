package org.library.ui;

import org.library.model.Book;
import org.library.model.Author;
import org.library.model.Genre;
import org.library.service.AuthorService;
import org.library.service.BookService;
import org.library.service.GenreService;
import org.library.service.MessageService;
import org.springframework.stereotype.Component;

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
        }
    }

    private void displayBooks() {
        List<Book> books = bookService.fetchAllBooks();
        if (books.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.library"));
        } else {
            System.out.printf("\n%-5s %-60s %-40s %-40s %s\n\n",
                    messageService.get("book.id"), messageService.get("book.title"), messageService.get("book.author"),
                    messageService.get("book.genre"), messageService.get("book.description"));
            books.forEach(System.out::println);
            System.out.println("\n✅  " + messageService.get("info.displayed.books", books.size()));
        }
    }

    private void createBook() {
        try {
            int newID = bookService.fetchAllBooks().stream()
                    .mapToInt(Book::getId)
                    .max()
                    .orElse(0) + 1;

            String title = getInputString(messageService.get("prompt.new.title"));
            Author author = promptForAuthor();
            Genre genre = promptForGenre();
            String description = getInputString(messageService.get("prompt.new.description"));
            Book book = new Book(newID, title, author, genre, description);
            bookService.addNewBook(book);

            System.out.println("\n✅  " + messageService.get("info.book.added",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getDescription()));
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + messageService.get("error.add.book") + "\n%s\n", e.getMessage());
        }
    }

    private void editBook() {
        int id = getInputInt("\n" + messageService.get("prompt.edit.id"));
        try {
            Book bookToEdit = bookService.fetchAllBooks().stream()
                    .filter(b -> b.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(messageService.get("error.book.not.found")));

            System.out.println("\n" + messageService.get("book.details.header"));
            System.out.println(bookToEdit);

            String title = getInputString(messageService.get("prompt.edit.title"));
            if (!title.isEmpty()) {
                bookToEdit.setTitle(title);
            }

            Author author = promptForAuthor();
            if (author != null) {
                bookToEdit.setAuthor(author);
            }

            Genre genre = promptForGenre();
            if (genre != null) {
                bookToEdit.setGenre(genre);
            }

            String description = getInputString(messageService.get("prompt.edit.description"));
            if (!description.isEmpty()) {
                bookToEdit.setDescription(description);
            }

            bookService.modifyBookById(id, bookToEdit);
            System.out.println("\n✅  " + messageService.get("info.book.updated",
                    bookToEdit.getId(), bookToEdit.getTitle(), bookToEdit.getAuthor().getName(), bookToEdit.getDescription()));
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + messageService.get("error.update.book") + " %d\n%s\n", id, e.getMessage());
        }
    }

    private void deleteBook() {
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

    private void displayAuthors() {
        List<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.authors"));
        } else {
            System.out.println("\n" + messageService.get("author.list.header"));
            authors.forEach(author -> System.out.println(author.getId() + ": " + author.getName()));
        }
    }

    private void displayGenres() {
        List<Genre> genres = genreService.findAll();
        if (genres.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.genres"));
        } else {
            System.out.println("\n" + messageService.get("genre.list.header"));
            genres.forEach(genre -> System.out.println(genre.getId() + ": " + genre.getName()));
        }
    }

    private Author promptForAuthor() {
        displayAuthors();
        int authorId = getInputInt(messageService.get("prompt.new.author"));
        return authorService.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException(messageService.get("error.invalid.author")));
    }

    private Genre promptForGenre() {
        displayGenres();
        int genreId = getInputInt(messageService.get("prompt.new.genre"));
        return genreService.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException(messageService.get("error.invalid.genre")));
    }

    private int getInputInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print(messageService.get("error.invalid.input"));
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }

    private String getInputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
