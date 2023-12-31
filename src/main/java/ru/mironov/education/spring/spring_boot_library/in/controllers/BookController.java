package ru.mironov.education.spring.spring_boot_library.in.controllers;
// package должен содержать в себе только прописные буквы

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mironov.education.spring.spring_boot_library.models.Book;
import ru.mironov.education.spring.spring_boot_library.models.Person;
import ru.mironov.education.spring.spring_boot_library.services.BookService;
import ru.mironov.education.spring.spring_boot_library.services.PersonService;
import ru.mironov.education.spring.spring_boot_library.usecase.GetAllBooks;


import java.util.Optional;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final PersonService personService;
    private final GetAllBooks getAllBooksUseCase;

    @GetMapping()
    public String getAllBooks(Model model) {
        return getAllBooksUseCase.execute(model);
    }

    @GetMapping("/{id}")
    public String showUnit(@PathVariable("id") int id,
                           Model model) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        if (book.getOwner().isPresent()) {
            model.addAttribute("owner", book.getOwner().get());
        } else {
            model.addAttribute("people", personService.findAll());
        }

        return "/books/showUnit";
    }

    //Возвращает страницу с формой для добавления новой книги && Post запрос на добавление книги в БД
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/newBook";
    }

    @PostMapping()
    public String newBookPost(@ModelAttribute("book") Book newBook) {
        bookService.save(newBook);

        return "redirect:/books";
    }

    //Возвращает страницу с формой для обновления существующей книги && Patch запрос на изменение книги в БД
    @GetMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));

        return "/books/editBook";
    }

    @PatchMapping("{id}/edit")
    public String updateBookPatch(@PathVariable("id") int id,
                                  @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books/{id}";
    }

    //Delete запрос на удаление книги из БД
    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    //Patch запросы на освобождение/назначение нового владельца для книги по её id
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id, HttpServletRequest request) {
        bookService.updateOwner(id, null);
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl).orElse("/");
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("newOwner") Person newOwner) {
        bookService.updateOwner(id, newOwner);
        return "redirect:/books/{id}";
    }

    //Возвращает страницу поиска && Post запрос на поиск книг, содержащих в названии query
    @GetMapping("/search")
    public String searchPage() {
        return "/books/search";
    }

    @PostMapping("/search")
    public String makeSearchPage(Model model, @RequestParam("query") String query) {
        model.addAttribute("foundBooks", bookService.findByTitle(query));
        return "/books/search";
    }
}
