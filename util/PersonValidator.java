package ru.kononov.springcourseSecurity.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kononov.springcourseSecurity.models.Person;
import ru.kononov.springcourseSecurity.services.PersonDetailsService;
@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return  Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
Person person=(Person)o;
try {
    personDetailsService.loadUserByUsername(person.getUsername());
}catch (UsernameNotFoundException ignore){
    return;
}
errors.rejectValue("username",""," человек с таким именем уже существует");

}}
