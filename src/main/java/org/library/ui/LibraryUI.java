package org.library.ui;

import org.library.model.Book;
import org.library.service.BookService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class LibraryUI {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public LibraryUI(BookService bookService) {
        this.bookService = bookService;
    }

    public void start() {
        while (true) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Display book list");
            System.out.println("2. Create new book");
            System.out.println("3. Edit book");
            System.out.println("4. Delete book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            String choiceInput = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> displayBooks();
                    case 2 -> createBook();
                    case 3 -> editBook();
                    case 4 -> deleteBook();
                    case 5 -> {
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (IOException e) {
                scanner.close();
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayBooks() throws IOException {
        List<Book> books = bookService.getBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(book -> System.out.printf("%d - %s by %s%n", book.getId(), book.getTitle(), book.getAuthor()));
        }
    }

    private void createBook() throws IOException {
        System.out.print("Enter book id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        bookService.addBook(new Book(id, title, author, description));
        System.out.println("Book added!");
    }

    private void editBook() throws IOException {
        System.out.print("Enter book id to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new title: ");
        String title = scanner.nextLine();

        System.out.print("Enter new author: ");
        String author = scanner.nextLine();

        System.out.print("Enter new description: ");
        String description = scanner.nextLine();

        bookService.updateBook(id, new Book(id, title, author, description));
        System.out.println("Book updated!");
    }

    private void deleteBook() throws IOException {
        System.out.print("Enter book id to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        bookService.deleteBook(id);
        System.out.println("Book deleted!");
    }
}
