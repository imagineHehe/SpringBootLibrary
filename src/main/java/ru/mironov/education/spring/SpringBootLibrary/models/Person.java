package ru.mironov.education.spring.SpringBootLibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Имя не может быть пустым")
    @Size(min = 10, message = "Длина имени должна быть не меньше 10")
    @Size(max = 60, message = "Длина имени должны быть не больше 60")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Имя должно быть в следующем формате: Фамилия Имя Отчество")
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Max(value = 2023, message = "Год рождения не может быть позже текущего")
    @Min(value = 1920, message = "Год рождения не может быть раньше 1920")
    @Column(name = "birth_year")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books = new ArrayList<>();

    public Person() {
    }
    public Person(String full_name, int birthYear) {
        this.fullName = full_name;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    private void setBooks(List<Book> books) {
        this.books = books;
    }

    protected void addBook(Book book){
        books.add(book);
        if(!book.getOwner().isPresent()){
            book.setOwner(this);
        }
    }
}
