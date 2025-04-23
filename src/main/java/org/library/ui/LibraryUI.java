package org.library.ui;

import org.library.model.Book;
import org.library.service.BookService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Component
public class LibraryUI {
    private final BookService bookService;
    private final MessageSource messageSource;
    private final Scanner scanner = new Scanner(System.in);
    private Locale locale;

    public LibraryUI(BookService bookService, MessageSource messageSource) {
        this.bookService = bookService;
        this.messageSource = messageSource;
    }

    public void start(Locale locale) {
        this.locale = locale;

        while (true) {
            System.out.println("\n" + msg("menu.header"));
            System.out.println("1. " + msg("menu.display"));
            System.out.println("2. " + msg("menu.create"));
            System.out.println("3. " + msg("menu.edit"));
            System.out.println("4. " + msg("menu.delete"));
            System.out.println("5. " + msg("menu.exit"));

            int choice = getInputInt("\n" + msg("prompt.choice"));
            try {
                switch (choice) {
                    case 1 -> displayBooks();
                    case 2 -> createBook();
                    case 3 -> editBook();
                    case 4 -> deleteBook();
                    case 5 -> {
                        System.out.println(msg("exit.message"));
                        scanner.close();
                        return;
                    }
                    default -> System.out.println(msg("error.invalid.choice"));
                }
            } catch (IOException e) {
                System.err.println(msg("error.generic") + e.getMessage());
                scanner.close();
            }
        }
    }

    private void displayBooks() throws IOException {
        List<Book> books = bookService.fetchAllBooks();
        if (books.isEmpty()) {
            System.out.println("\n❌  " + msg("info.empty.library"));
        } else {
            System.out.printf("\n%-5s %-40s %-40s %s\n\n",
                    msg("book.id"), msg("book.title"), msg("book.author"), msg("book.description"));
            books.forEach(System.out::println);
            System.out.printf("\n✅  " + msg("info.displayed.books") + "\n", books.size());
        }
    }

    private void createBook() throws IOException {
        try {
            List<Book> books = bookService.fetchAllBooks();
            int newId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;

            String title = getInputString("\n" + msg("prompt.new.title"));
            String author = getInputString(msg("prompt.new.author"));
            String description = getInputString(msg("prompt.new.description"));

            Book book = new Book(newId, title, author, description);
            bookService.addNewBook(book);

            System.out.printf("\n✅  " + msg("info.book.added") + "\n",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + msg("error.add.book") + "\n%s\n", e.getMessage());
        }
    }

    private void editBook() throws IOException {
        int id = getInputInt("\n" + msg("prompt.edit.id"));
        try {
            List<Book> books = bookService.fetchAllBooks();
            Book bookToEdit = books.stream()
                    .filter(b -> b.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(msg("error.book.not.found") + id));

            bookToEdit.setTitle(getInputString(msg("prompt.edit.title")));
            bookToEdit.setAuthor(getInputString(msg("prompt.edit.author")));
            bookToEdit.setDescription(getInputString(msg("prompt.edit.description")));

            bookService.modifyBookById(id, bookToEdit);
            System.out.printf("\n✅  " + msg("info.book.updated") + "\n",
                    bookToEdit.getId(), bookToEdit.getTitle(), bookToEdit.getAuthor(), bookToEdit.getDescription());
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + msg("error.update.book") + " %d\n%s\n", id, e.getMessage());
        }
    }

    private void deleteBook() throws IOException {
        int id = getInputInt("\n" + msg("prompt.delete.id"));
        try {
            Book deletedBook = bookService.fetchAllBooks().stream()
                    .filter(book -> book.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(msg("error.book.not.found") + id));

            bookService.removeBookById(id);
            System.out.printf("\n✅  " + msg("info.book.deleted") + "\n",
                    deletedBook.getId(), deletedBook.getTitle(), deletedBook.getAuthor(), deletedBook.getDescription());
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + msg("error.delete.book") + " %d\n%s\n", id, e.getMessage());
        }
    }

    private int getInputInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print(msg("error.invalid.input"));
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

    private String msg(String key) {
        return messageSource.getMessage(key, null, locale);
    }
}
