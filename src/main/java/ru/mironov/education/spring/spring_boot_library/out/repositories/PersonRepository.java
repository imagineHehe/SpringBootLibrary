package ru.mironov.education.spring.spring_boot_library.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mironov.education.spring.spring_boot_library.models.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    boolean existsPersonByFullName(String name);
    String QUERY = """
            SELECT * FROM Person p
            LEFT JOIN Book b on p.book_id=b.id
            WHERE p.id = ?1
            """;

//    @Query("SELECT p FROM Person p left join fetch p.books")
    @Query(value = QUERY, nativeQuery = true)
    Person getByIdEager(int id);
}
