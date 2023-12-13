package ru.mironov.education.spring.spring_boot_library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mironov.education.spring.spring_boot_library.models.Book;
import ru.mironov.education.spring.spring_boot_library.models.Person;
import ru.mironov.education.spring.spring_boot_library.out.repositories.BookRepository;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceJpa implements BookService {
    private final BookRepository bookRepository;
    private final PersonService personService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findByTitle(String query) {
        if (query.length() < 3) {
            return new ArrayList<>();
        }
        return bookRepository.findByTitleContainingIgnoreCase(query);
    }

    @Override
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void updateOwner(int id, Person selectedPerson) {
        Book book = findOne(id);
        if (selectedPerson == null) {
            book.setOwner(null);
            book.setDateOfTaking(null);
        } else {
            Person newOwner = personService.findOne(selectedPerson.getId());
            book.setOwner(newOwner);
        }

    }

    @Override
    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }
}
