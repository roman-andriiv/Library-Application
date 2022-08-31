package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Roman_Andriiv
 */
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> searchByTitle(String query){
        return booksRepository.findByTitleStartingWith(query);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setBookOwner(bookToBeUpdated.getBookOwner());

        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    //Return null if book has no owner
    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getBookOwner).orElse(null);
    }

    //Releases the book (this method is called when a person returns a book to the library)
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setBookOwner(null);
                    book.setTakenAt(null);
                });
    }

    //Assigns a book to a person (this method is called when a person checks out a book from the library)
    public void assign(int id, Person selectedPerson) {
        booksRepository.findById(id).ifPresent(book -> {
                    book.setBookOwner(selectedPerson);
                    book.setTakenAt(new Date());
        });
    }
}
