package org.library.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.library.model.Author;
import org.library.repository.AuthorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public class HibernateAuthorRepository implements AuthorRepository {

    private final SessionFactory sessionFactory;

    public HibernateAuthorRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Author> read() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Author", Author.class).list();
        }
    }

    @Override
    public void create(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(author);
            tx.commit();
        }
    }

    @Override
    public void update(Author author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(author);
            tx.commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Author author = session.get(Author.class, id);
            if (author != null) {
                session.delete(author);
            }
            tx.commit();
        }
    }

    @Override
    public Optional<Author> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Author.class, id));
        }
    }
}
