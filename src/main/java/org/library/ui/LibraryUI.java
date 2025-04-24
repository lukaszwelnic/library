package org.library.ui;

import org.library.model.Book;
import org.library.service.BookService;
import org.library.service.MessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class LibraryUI {
    private final BookService bookService;
    private final MessageService messageService;
    private final Scanner scanner = new Scanner(System.in);

    public LibraryUI(BookService bookService, MessageService messageService) {
        this.bookService = bookService;
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

    private void displayBooks() throws IOException {
        List<Book> books = bookService.fetchAllBooks();
        if (books.isEmpty()) {
            System.out.println("\n❌  " + messageService.get("info.empty.library"));
        } else {
            System.out.printf("\n%-5s %-40s %-40s %s\n\n",
                    messageService.get("book.id"), messageService.get("book.title"), messageService.get("book.author"), messageService.get("book.description"));
            books.forEach(System.out::println);
            System.out.println("\n✅  " + messageService.get("info.displayed.books", books.size()));
        }
    }

    private void createBook() throws IOException {
        try {
            List<Book> books = bookService.fetchAllBooks();
            int newId = books.stream().mapToInt(Book::getId).max().orElse(0) + 1;

            String title = getInputString("\n" + messageService.get("prompt.new.title"));
            String author = getInputString(messageService.get("prompt.new.author"));
            String description = getInputString(messageService.get("prompt.new.description"));

            Book book = new Book(newId, title, author, description);
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

            bookToEdit.setTitle(getInputString(messageService.get("prompt.edit.title")));
            bookToEdit.setAuthor(getInputString(messageService.get("prompt.edit.author")));
            bookToEdit.setDescription(getInputString(messageService.get("prompt.edit.description")));

            bookService.modifyBookById(id, bookToEdit);
            System.out.println("\n✅  " + messageService.get("info.book.updated",
                    bookToEdit.getId(), bookToEdit.getTitle(), bookToEdit.getAuthor(), bookToEdit.getDescription()));
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
                    deletedBook.getId(), deletedBook.getTitle(), deletedBook.getAuthor(), deletedBook.getDescription()));
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  " + messageService.get("error.delete.book") + " %d\n%s\n", id, e.getMessage());
        }
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
