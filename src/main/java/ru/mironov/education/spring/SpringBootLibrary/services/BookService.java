package ru.mironov.education.spring.SpringBootLibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mironov.education.spring.SpringBootLibrary.models.Book;
import ru.mironov.education.spring.SpringBootLibrary.models.Person;
import ru.mironov.education.spring.SpringBootLibrary.repositories.BookRepository;
import ru.mironov.education.spring.SpringBootLibrary.repositories.PersonRepository;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonService personService;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository, PersonService personService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }
    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }
    public List<Book> findByTitle(String query){
        if(query.length() < 3){
            return new ArrayList<>();
        }
        return bookRepository.findByTitleContainingIgnoreCase(query);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }
//    @Transactional
//    public void release(int id){
//        Book book = findOne(id);
//        book.setOwner(null);
//    }
//    @Transactional
//    public void assign(int id, Person selectedPerson){
//        Book book = findOne(id);
//        Person newOwner = personService.findOne(selectedPerson.getId());
//        book.setOwner(newOwner);
//    }
    @Transactional
    public void updateOwner(int id, Person selectedPerson){
        Book book = findOne(id);
        if(selectedPerson == null){
            book.setOwner(null);
            book.setDateOfTaking(null);
        }else {
            Person newOwner = personService.findOne(selectedPerson.getId());
            book.setOwner(newOwner);
        }

    }
    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }
}
