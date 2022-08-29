package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
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
public class BookService {

    private final BooksRepository booksRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(BooksRepository booksRepository, JdbcTemplate jdbcTemplate) {
        this.booksRepository = booksRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return booksRepository.findAll();
    }

    public Book show(int id) {

        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
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
