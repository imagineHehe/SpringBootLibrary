package ru.mironov.education.spring.spring_boot_library.services;

import ru.mironov.education.spring.spring_boot_library.models.Book;
import ru.mironov.education.spring.spring_boot_library.models.Person;

import java.util.List;


public interface BookService {
    List<Book> findAll();

    Book findOne(int id);

    List<Book> findByTitle(String query);

    void save(Book book);

    void update(int id, Book updatedBook);

    void updateOwner(int id, Person selectedPerson);

    void delete(int id);
}
