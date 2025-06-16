package org.library.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public class HibernateBookRepository implements BookRepository {

    private final SessionFactory sessionFactory;

    public HibernateBookRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Book> read() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).list();
        }
    }

    @Override
    public Book create(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(book);
            tx.commit();
            return book;
        }
    }

    @Override
    public void update(int id, Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            book.setId(id);
            session.merge(book);
            tx.commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.remove(book);
            }
            tx.commit();
        }
    }

    @Override
    public Optional<Book> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Book.class, id));
        }
    }
}
