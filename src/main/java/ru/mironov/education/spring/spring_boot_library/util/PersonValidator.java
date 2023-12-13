package ru.mironov.education.spring.spring_boot_library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mironov.education.spring.spring_boot_library.models.Person;
import ru.mironov.education.spring.spring_boot_library.services.PersonService;


@Component
public class PersonValidator implements Validator {
    private final PersonService personService;
    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if(personService.nameIsUsed(person.getFullName())){
            errors.rejectValue("fullName", "", "Данное имя уже использовано");
        }
    }
    public void validateUpdate(Object o, int id, Errors errors) {
        Person person = (Person) o;
        Person personToBeUpdate = personService.findOne(id);
        if(!person.getFullName().equals(personToBeUpdate.getFullName()) && personService.nameIsUsed(person.getFullName())){
            errors.rejectValue("fullName", "", "Данное имя уже использовано");
        }
    }

}
