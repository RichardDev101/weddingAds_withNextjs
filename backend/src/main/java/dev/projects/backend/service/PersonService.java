package dev.projects.backend.service;

import dev.projects.backend.collection.Person;
import dev.projects.backend.enums.LoginRole;
import dev.projects.backend.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final GenerateUUIDService uuid;

    public Person saveAdmin(Person person) {
        person.setPersonId(uuid.getUUID());
        person.setLoginRole(LoginRole.ADMIN);
        return personRepository.save(person);
    }
    public Person saveEditor(Person person) {
        person.setPersonId(uuid.getUUID());
        person.setLoginRole(LoginRole.EDITOR);
        return personRepository.save(person);
    }

    public Person saveUser(Person person) {
        person.setPersonId(uuid.getUUID());
        person.setLoginRole(LoginRole.USER);
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Person> getPersonsByRole(LoginRole role) {
        return personRepository.findByLoginRoleIs(role);
    }

    public Person getPersonById(String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()){
            return optionalPerson.get();
        } else {
            throw new NoSuchElementException();
        }
    }
    public Person update(Person person, String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()){
            throw new NoSuchElementException();
        }else {
            return personRepository.save(person);
        }
    }
    public void delete(String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()){
            throw new NoSuchElementException();
        }else {
            personRepository.deleteById(id);
        }
    }

}
