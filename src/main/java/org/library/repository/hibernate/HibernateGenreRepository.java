package org.library.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.library.model.Genre;
import org.library.repository.GenreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("hibernate")
public class HibernateGenreRepository implements GenreRepository {

    private final SessionFactory sessionFactory;

    public HibernateGenreRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Genre> read() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Genre", Genre.class).list();
        }
    }

    @Override
    public void create(Genre genre) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(genre);
            tx.commit();
        }
    }

    @Override
    public void update(Genre genre) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(genre);
            tx.commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Genre genre = session.get(Genre.class, id);
            if (genre != null) {
                session.delete(genre);
            }
            tx.commit();
        }
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Genre.class, id));
        }
    }
}
