package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Roman_Andriiv
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, JdbcTemplate jdbcTemplate) {
        this.peopleRepository = peopleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return peopleRepository.findAll();
    }


    public Person show(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
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
