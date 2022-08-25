package org.example.models;

import org.hibernate.annotations.Tables;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Roman_Andriiv
 */
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Book title ")
    @Size(min = 2, max = 100, message = "Book title should not be empty must be between 2 and 100 characters long")
    @Column(name="title")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters long")
    @Column(name="author")
    private String author;

    @Min(value = 1700, message = "Year must be greater than 1700")
    @Column(name="year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person bookOwner;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getBookOwner() {
        return bookOwner;
    }

    public void setBookOwner(Person bookOwner) {
        this.bookOwner = bookOwner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", owner=" + bookOwner +
                '}';
    }
}
