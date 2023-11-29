package ru.mironov.education.spring.SpringBootLibrary.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mironov.education.spring.SpringBootLibrary.models.Person;
import ru.mironov.education.spring.SpringBootLibrary.services.PersonService;
import ru.mironov.education.spring.SpringBootLibrary.util.PersonValidator;


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
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "/people/newPerson";
    }
    @PostMapping()
    public String newPersonPost(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/newPerson";
        }
        personService.save(person);
        return "redirect:/people";
    }
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
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id")int id){
        personService.delete(id);
        return "redirect:/people";
    }

}
