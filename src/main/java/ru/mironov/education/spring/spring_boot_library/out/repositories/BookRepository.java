package ru.mironov.education.spring.spring_boot_library.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mironov.education.spring.spring_boot_library.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainingIgnoreCase(String title);
}
