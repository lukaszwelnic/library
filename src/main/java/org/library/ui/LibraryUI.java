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
            System.out.println("1. Display books");
            System.out.println("2. Add book");
            System.out.println("3. Edit book");
            System.out.println("4. Delete book");
            System.out.println("5. Exit");

            int choice = getInputInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1 -> displayBooks();
                    case 2 -> addBook();
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
                System.err.println("Error: " + e.getMessage());
                scanner.close();
            }
        }
    }

    private void displayBooks() throws IOException {
        List<Book> books = bookService.getBooks();
        if (books.isEmpty()){
            System.out.print("\n❌  Library is empty. Add books first!\n");
        } else{
            System.out.printf("\n%-5s %-40s %-40s %s\n\n", "ID", "Title", "Author", "Description");
            books.forEach(System.out::println);
            System.out.printf("\n✅  Displayed %d books\n", books.size());
        }
    }

    private void addBook() throws IOException {
        try {
            List<Book> books = bookService.getBooks();
            int newId = books.stream()
                    .mapToInt(Book::getId)
                    .max()
                    .orElse(0) + 1; // Auto-increment ID

            String title = getInputString("\nEnter new title: ");
            String author = getInputString("Enter new author: ");
            String description = getInputString("Enter new description: ");

            Book book = new Book(newId, title, author, description);
            bookService.addBook(book);

            System.out.printf("\n✅  Book added successfully: ID: %d, Title: %s, Author: %s, Description: %s\n",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  Failed to add a book\n%s\n", e.getMessage());
        }
    }

    private void editBook() throws IOException {
        int id = getInputInt("\nEnter book ID to edit: ");
        try {
            List<Book> books = bookService.getBooks();
            Book bookToEdit = books.stream()
                    .filter(b -> b.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Book ID " + id + " not found."));

            bookToEdit.setTitle(getInputString("Enter updated title: "));
            bookToEdit.setAuthor(getInputString("Enter updated author: "));
            bookToEdit.setDescription(getInputString("Enter updated description: "));

            bookService.updateBook(id, bookToEdit);
            System.out.printf("\n✅  Book updated successfully: ID: %d, Title: %s, Author: %s, Description: %s\n",
                    bookToEdit.getId(), bookToEdit.getTitle(), bookToEdit.getAuthor(), bookToEdit.getDescription());
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  Failed to update book with ID %d\n%s\n", id, e.getMessage());
        }
    }

    private void deleteBook() throws IOException {
        int id = getInputInt("\nEnter book ID to delete: ");
        try {
            Book deletedBook = bookService.getBooks().stream()
                    .filter(book -> book.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Book ID " + id + " not found."));

            bookService.deleteBook(id);
            System.out.printf("\n✅  Book deleted successfully: ID: %d, Title: %s, Author: %s, Description: %s\n",
                    deletedBook.getId(), deletedBook.getTitle(), deletedBook.getAuthor(), deletedBook.getDescription());
        } catch (IllegalArgumentException e) {
            System.err.printf("\n❌  Failed to delete book with ID %d\n%s\n", id, e.getMessage());
        }
    }

    private int getInputInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Enter a number: ");
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
