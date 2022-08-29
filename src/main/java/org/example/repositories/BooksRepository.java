package org.example.repositories;

import org.example.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Roman_Andriiv
 */
@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

}
