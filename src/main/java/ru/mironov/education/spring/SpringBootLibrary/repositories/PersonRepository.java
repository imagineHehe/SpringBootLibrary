package ru.mironov.education.spring.SpringBootLibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mironov.education.spring.SpringBootLibrary.models.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    boolean existsPersonByFullName(String name);
}
