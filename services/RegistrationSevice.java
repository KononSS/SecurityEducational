package ru.kononov.springcourseSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kononov.springcourseSecurity.models.Person;
import ru.kononov.springcourseSecurity.repositories.PeopleRepository;

@Service
public class RegistrationSevice {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationSevice(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }
@Transactional
    public void  register(Person person){

    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setRole("ROLE_USER");

    peopleRepository.save(person);
}
}
