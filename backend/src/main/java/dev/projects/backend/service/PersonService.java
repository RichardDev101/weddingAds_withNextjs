package dev.projects.backend.service;

import dev.projects.backend.collection.Person;
import dev.projects.backend.enums.LoginRole;

import java.util.List;

public interface PersonService {
    Person saveUser(Person person);
    Person saveAdmin(Person person);
    Person saveEditor(Person person);
    List<Person> getAllPersons();
    Person getPersonById(String id);
    List<Person> getPersonsByRole(LoginRole role);
    Person update(Person person, String id);
    void delete(String id);

}
