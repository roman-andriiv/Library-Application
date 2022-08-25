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
public class PersonDAO {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory, JdbcTemplate jdbcTemplate) {
        this.sessionFactory = sessionFactory;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {

        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {

        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);

        personToBeUpdated.setFullName(updatedPerson.getFullName());
        personToBeUpdated.setYearOfBirth(updatedPerson.getYearOfBirth());
    }

    @Transactional
    public void delete(int id) {

        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    //for the uniqueness validation of the full name
    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?",
                new BeanPropertyRowMapper<>(Person.class), fullName).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?",
                new BeanPropertyRowMapper<>(Book.class), id);
    }
}
