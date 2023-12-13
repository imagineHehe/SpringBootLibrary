package ru.mironov.education.spring.spring_boot_library.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.mironov.education.spring.spring_boot_library.services.BookService;

@Service
@RequiredArgsConstructor
public class GetAllBooks {
    private final BookService bookService;

    public String execute(Model model) {
        model.addAttribute("books", bookService.findAll());

        return "/books/showAll";
    }
}
