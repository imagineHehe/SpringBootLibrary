package ru.mironov.education.spring.spring_boot_library.in.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mironov.education.spring.spring_boot_library.models.Person;
import ru.mironov.education.spring.spring_boot_library.services.PersonService;
import ru.mironov.education.spring.spring_boot_library.util.PersonValidator;


@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }
    //Возвращает страницу со всеми клиентами/с клиентом по его id
    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("people", personService.findAll());
        return "/people/showAll";
    }
    @GetMapping("/{id}")
    public String showUnit(@PathVariable("id") int id, Model model){
        Person person = personService.findOne(id);
        model.addAttribute("person", person);
        model.addAttribute("books", person.getBooks());
        return "/people/showUnit";
    }

    //Возвращает страницу с формой для добавления нового клиента && Post запрос на добавление клиента в БД
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/newPerson";
    }
    @PostMapping()
    public String newPersonPost(@ModelAttribute("person") @Valid Person newPerson, BindingResult bindingResult){
        personValidator.validate(newPerson, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/newPerson";
        }
        personService.save(newPerson);
        return "redirect:/people";
    }

    //Возвращает страницу с формой для обновления существующего клиента && Patch запрос на изменение данных клиента в БД
    @GetMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.findOne(id));
        return "/people/editPerson";
    }
    @PatchMapping("/{id}")
    public String updatePersonPatch(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                                    @PathVariable("id") int id){
        personValidator.validateUpdate(person, id, bindingResult);
        if(bindingResult.hasErrors()){
            return "/people/editPerson";
        }
        personService.update(id, person);
        return "redirect:/people/{id}";
    }

    //Delete запрос на удаление клиента из БД
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id")int id){
        personService.delete(id);
        return "redirect:/people";
    }

}
