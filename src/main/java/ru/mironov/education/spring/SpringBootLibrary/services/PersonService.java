package ru.mironov.education.spring.SpringBootLibrary.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mironov.education.spring.SpringBootLibrary.models.Person;
import ru.mironov.education.spring.SpringBootLibrary.repositories.PersonRepository;


import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }
    public Person findOne(int id){
        Person person = personRepository.findById(id).orElse(null);
        Hibernate.initialize(person.getBooks());
        return person;
    }
    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }
    public boolean nameIsUsed(String fullName){
        return personRepository.existsPersonByFullName(fullName);
    }
}
