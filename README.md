# Library CRUD Console Application

A modular **Library CRUD console application** for managing book data, currently supporting **PostgreSQL** storage. The application is designed using **Spring (Context only)** for dependency injection, and includes configurable AOP features like **logging** and **caching**.

## Features

- **Display Book List**: View all stored books.
- **Create New Book**: Add a new book.
- **Edit Existing Book**: Update details of a book by ID.
- **Delete Book**: Remove a book by ID.
- **Command-line Interface**: Simple text-based user interface for interaction.
- **Persistent Storage Options**:
  - **PostgreSQL**: Query a database using Spring JDBC.
- **Profile-based Configuration**:
  - Enable or disable logging and caching aspects.

## Technologies Used

- **Java**: Core programming language.
- **Spring Context**: Dependency injection and configuration management.
- **Spring JDBC**: For PostgreSQL integration.
- **AspectJ**: For AOP logging and caching.
- **Gradle**: Project build and dependency management.

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/lukaszwelnic/library.git
cd library
````

### 2. Build the Project

```bash
./gradlew clean build
```

### 3. Configure Active Profiles

Edit `src/main/resources/application.properties` to choose the desired profiles:

```properties
# Example (to disable logging or caching delete "logging/caching"
spring.profiles.active=logging,caching,jdbc
```

### 4. Set up PostgreSQL

* Create a new user, database and grant privileges.
* Run the SQL initialization scripts in:
  * `src/main/resources/schema.sql` to create tables.
  * `src/main/resources/authors_insert.sql` to insert authors.
  * `src/main/resources/genres_insert.sql` to insert genres.
  * `src/main/resources/books_insert.sql` to insert books.

### 5. Run the Application

```bash
./gradlew run
```

## Usage

You’ll be presented with a menu of options:

```
Library Menu:
1. Display book list
2. Create new book
3. Edit book
4. Delete book
5. Exit
Enter choice: 
```

## TODO

* Add CSV support
* Add exceptions and exception handling
* Add different messages with tick and x to messageService
* Check for duplicate authors/genres
* Validate input
* Allow user to add/update/delete new Genres and Authors

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.