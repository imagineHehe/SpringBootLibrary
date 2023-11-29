package ru.mironov.education.spring.SpringBootLibrary.models;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private int year;
    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "is_taken_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfTaking;

    public Book() {
    }

    public Book(String title, String author, int year, int person_id) {
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

    public Optional<Person> getOwner() {
        return Optional.ofNullable(owner);
    }

    public Date getDateOfTaking() {
        return dateOfTaking;
    }
    public boolean isOverdue() {
        Calendar dateOfOverdue = Calendar.getInstance();
        dateOfOverdue.setTime(dateOfTaking);
        dateOfOverdue.add(Calendar.MINUTE, 1);
        return dateOfOverdue.getTime().before(new Date());
    }

    public void setDateOfTaking(Date dateOfTaking) {
        this.dateOfTaking = dateOfTaking;
    }

    public void setOwner(Person owner) {
        if(this.owner != null){
            this.owner.getBooks().remove(this);
        }
        this.owner = owner;
        if(this.owner != null && !this.owner.getBooks().contains(this)){
            dateOfTaking = new Date();
            owner.addBook(this);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", owner=" + owner +
                ", dateOfTaking=" + dateOfTaking +
                '}';
    }
}
