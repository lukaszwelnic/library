# Library CRUD Console Application

A modular **Library CRUD console application** for managing book data, currently supporting both **CSV file** and **PostgreSQL** storage. The application is designed using **Spring (Context only)** for dependency injection, and includes configurable AOP features like **logging** and **caching**.

## Features

- **Display Book List**: View all stored books.
- **Create New Book**: Add a new book.
- **Edit Existing Book**: Update details of a book by ID.
- **Delete Book**: Remove a book by ID.
- **Command-line Interface**: Simple text-based user interface for interaction.
- **Persistent Storage Options**:
  - **CSV**: Read/write to a local CSV file.
  - **PostgreSQL**: Query a database using Spring JDBC (optional).
- **Profile-based Configuration**:
  - Easily switch between CSV or JDBC storage using Spring profiles.
  - Enable or disable logging and caching aspects.

## Technologies Used

- **Java**: Core programming language.
- **Spring Context**: Dependency injection and configuration management.
- **Jackson CSV Mapper**: CSV serialization and deserialization.
- **Spring JDBC** (optional): For PostgreSQL integration.
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

```
# Example
spring.profiles.active=logging,caching,csv
# or to use PostgreSQL instead of CSV
spring.profiles.active=logging,caching,jdbc
```

### 4. (Optional) Set up PostgreSQL

* Create a new database.
* Run the SQL initialization script in `src/main/resources/schema.sql` to create tables.

### 5. Run the Application

```bash
./gradlew run
```

---

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

---

## Architecture Overview

### Repository Interface

* `BookRepository`: Common interface for all storage types.
* `CsvBookRepository`: CSV file implementation.
* `JdbcBookRepository`: PostgreSQL JDBC implementation.

### AOP Support

* `LoggingAspect`: Logs method calls (enabled with `logging` profile).
* `CachingAspect`: Caches method results (enabled with `caching` profile).

Use Spring profiles to toggle behavior.

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.