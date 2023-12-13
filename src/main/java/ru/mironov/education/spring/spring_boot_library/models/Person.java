package ru.mironov.education.spring.spring_boot_library.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

    public Person(String full_name, int birthYear) {
        this.fullName = full_name;
        this.birthYear = birthYear;
    }

    protected void addBook(Book book) {
        books.add(book);

        if (book.getOwner().isEmpty()) {
            book.setOwner(this);
        }
    }
}
