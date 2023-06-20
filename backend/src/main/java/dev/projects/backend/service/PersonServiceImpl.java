package dev.projects.backend.service;

import dev.projects.backend.collection.Person;
import dev.projects.backend.enums.LoginRole;
import dev.projects.backend.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GenerateUUIDService uuid;

    @Override
    public Person saveAdmin(Person person) {
        person.setPersonId(uuid.getUUID());
        person.setLoginRole(LoginRole.ADMIN);
        return personRepository.save(person);
    }
    @Override
    public Person saveEditor(Person person) {
        person.setPersonId(uuid.getUUID());
        person.setLoginRole(LoginRole.EDITOR);
        return personRepository.save(person);
    }

    @Override
    public Person saveUser(Person person) {
        person.setPersonId(uuid.getUUID());
        person.setLoginRole(LoginRole.USER);
        return personRepository.save(person);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> getPersonsByRole(LoginRole role) {
        return personRepository.findByLoginRoleIs(role);
    }

    @Override
    public Person getPersonById(String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()){
            return optionalPerson.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Person update(Person person, String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()){
            throw new NoSuchElementException();
        }else {
            return personRepository.save(person);
        }
    }

    @Override
    public void delete(String id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty()){
            throw new NoSuchElementException();
        }else {
            personRepository.deleteById(id);
        }
    }

}
