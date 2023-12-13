package ru.mironov.education.spring.spring_boot_library.services;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.mironov.education.spring.spring_boot_library.models.Book;
import ru.mironov.education.spring.spring_boot_library.models.Person;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@Profile("in-memory")
public class BookServiceMemory implements BookService {
    private final List<Book> books = new ArrayList<>();
    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Book findOne(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Book> findByTitle(String query) {
        return null;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void update(int id, Book updatedBook) {

    }

    @Override
    public void updateOwner(int id, Person selectedPerson) {

    }

    @Override
    public void delete(int id) {

    }
}
