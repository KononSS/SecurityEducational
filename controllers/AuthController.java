package ru.kononov.springcourseSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kononov.springcourseSecurity.models.Person;
import ru.kononov.springcourseSecurity.services.RegistrationSevice;
import ru.kononov.springcourseSecurity.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationSevice registrationSevice;
    private final PersonValidator personValidator;
@Autowired
    public AuthController(RegistrationSevice registrationSevice, PersonValidator personValidator) {
    this.registrationSevice = registrationSevice;
    this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person){
        return "auth/registration";
    }
@PostMapping("auth/registration")
    public String performRegistration(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult){
    personValidator.validate(person,bindingResult);
    if (bindingResult.hasErrors())
        return "/auth/registration";
    registrationSevice.register(person);
    return "redirect:/auth/login";
}
}
