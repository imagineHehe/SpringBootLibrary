package ru.mironov.education.spring.spring_boot_library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
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

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public boolean isOverdue() {
        Calendar dateOfOverdue = Calendar.getInstance();
        dateOfOverdue.setTime(dateOfTaking);
        dateOfOverdue.add(Calendar.DAY_OF_MONTH, 10);
        return dateOfOverdue.getTime().before(new Date());
    }

    public void setDateOfTaking(Date dateOfTaking) {
        this.dateOfTaking = dateOfTaking;
    }

    public Optional<Person> getOwner() {
        return Optional.ofNullable(owner);
    }

    public void setOwner(Person owner) {
        if (this.owner != null) {
            this.owner.getBooks().remove(this);
        }
        this.owner = owner;
        if (this.owner != null && !this.owner.getBooks().contains(this)) {
            dateOfTaking = new Date();
            owner.addBook(this);
        }
    }
}
