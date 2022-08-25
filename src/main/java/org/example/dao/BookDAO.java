package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Roman_Andriiv
 */
@Component
public class BookDAO {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional
    public void save(Book book) {

        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {

        Session session = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = session.get(Book.class, id);

        bookToBeUpdated.setTitle(updatedBook.getTitle());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setYear(updatedBook.getYear());
    }

    @Transactional
    public void delete(int id) {

        Session session = sessionFactory.getCurrentSession();

        session.remove(session.get(Book.class, id));
    }

    //Join Book and Person tables and get the person who owns the book with the specified id
    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.person_id = " +
                "person.id WHERE book.id = ?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    //Releases the book (this method is called when a person returns a book to the library)
    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE id =?", id);
    }

    //Assigns a book to a person (this method is called when a person checks out a book from the library)
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}
