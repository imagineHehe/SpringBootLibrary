package ru.mironov.education.spring.spring_boot_library.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mironov.education.spring.spring_boot_library.models.Person;
import ru.mironov.education.spring.spring_boot_library.out.repositories.PersonRepository;


import java.util.List;

@Data
@Slf4j
@Service
public class PersonService {
    private final PersonRepository personRepository;

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
        log.debug("Start to save person {}", person);

        personRepository.save(person);

        log.debug("Person saved : {}", person);
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
