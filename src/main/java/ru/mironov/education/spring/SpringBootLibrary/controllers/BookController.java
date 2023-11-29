package ru.mironov.education.spring.SpringBootLibrary.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mironov.education.spring.SpringBootLibrary.models.Book;
import ru.mironov.education.spring.SpringBootLibrary.models.Person;
import ru.mironov.education.spring.SpringBootLibrary.services.BookService;
import ru.mironov.education.spring.SpringBootLibrary.services.PersonService;


import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("books", bookService.findAll());
        return "/books/showAll";
    }
    @GetMapping("/{id}")
    public String showUnit(@PathVariable("id")int id, Model model,
                           @ModelAttribute("person") Person person){
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        if(book.getOwner().isPresent()){
            model.addAttribute("owner", book.getOwner().get());
        }else {
            model.addAttribute("people", personService.findAll());
        }
        return "/books/showUnit";
    }
    @GetMapping("/search")
    public String searchPage(){
        return "/books/search";
    }
    @PostMapping("/search")
    public String makeSearchPage(Model model, @RequestParam("query") String query){
        model.addAttribute("foundBooks", bookService.findByTitle(query));
        return "/books/search";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book){
        return "/books/newBook";
    }
    @PostMapping()
    public String newBookPost(@ModelAttribute("book") Book book){
        bookService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id, HttpServletRequest request){
        bookService.updateOwner(id, null);
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl).orElse("/");
        //return "redirect:/books/{id}";
    }
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("newOwner") Person newOwner){
        bookService.updateOwner(id, newOwner);
        return "redirect:/books/{id}";
    }
    @GetMapping("/{id}/edit")
    public String updateBook(@PathVariable("id")int id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "/books/editBook";
    }
    @PatchMapping("{id}/edit")
    public String updateBookPatch(@PathVariable("id")int id,
                                  @ModelAttribute("book")Book book){
        bookService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id")int id){
        bookService.delete(id);
        return "redirect:/books";
    }

}
