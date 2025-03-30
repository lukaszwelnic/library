# Library CRUD Console Application

A simple **Library CRUD console application** for storing book information using **CSV files**. The project implements basic CRUD operations (Create, Read, Update, Delete) to manage books, and utilizes **Spring** for dependency injection and **Jackson CSV Mapper** for interacting with CSV data.

## Features
- **Display Book List**: View all books stored in the CSV file.
- **Create New Book**: Add a new book entry.
- **Edit Existing Book**: Update the details of an existing book.
- **Delete Book**: Remove a book from the collection.
- **Persistent Data**: Books are saved to and loaded from a CSV file.
- **Command-line Interface**: Simple text-based user interface for interaction.

## Technologies
- **Java**: Core programming language.
- **Spring**: Dependency injection and application context management (using `spring-context`).
- **Jackson CSV Mapper**: For reading and writing books to CSV files.
- **Gradle**: Build tool for project management.

## Setup and Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/lukaszwelnic/library.git
   cd library
   ```

2. **Install Dependencies**
   If you have Gradle installed, run the following command to download and install the dependencies:
   ```bash
   ./gradlew clean build
   ```

3. **Run the Application**
   You can run the application using:
   ```bash
   ./gradlew run
   ```

## Usage

1. Once the program starts, you'll see a simple menu with 5 options:
    - **1**: Display book list
    - **2**: Create new book
    - **3**: Edit book
    - **4**: Delete book
    - **5**: Exit the application

2. **Displaying Books**:  
   Choose option `1` to display all books in the library.

3. **Creating a New Book**:  
   Choose option `2` to create a new book. You will be prompted to enter the book's `ID`, `Title`, `Author`, and `Description`.

4. **Editing a Book**:  
   Choose option `3` to edit an existing book. You will be prompted to enter the `ID` of the book you want to update, followed by new values for the `Title`, `Author`, and `Description`.

5. **Deleting a Book**:  
   Choose option `4` to delete a book. You will be prompted to enter the `ID` of the book to delete.

6. **Exit**:  
   Choose option `5` to exit the application.

### Example:
```
Library Menu:
1. Display book list
2. Create new book
3. Edit book
4. Delete book
5. Exit
Enter choice: 1
```

### Key Classes:
- **`Book.java`**: Defines the properties of a book (ID, Title, Author, Description).
- **`BookRepository.java`**: Manages the reading and writing of book data to the CSV file.
- **`BookService.java`**: Contains the CRUD logic for managing books.
- **`LibraryUI.java`**: Handles the user interface, including displaying the menu and capturing user input.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.