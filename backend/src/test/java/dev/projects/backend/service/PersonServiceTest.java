package dev.projects.backend.service;

import dev.projects.backend.collection.Person;
import dev.projects.backend.enums.LoginRole;
import dev.projects.backend.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    PersonRepository personRepository = mock(PersonRepository.class);
    GenerateUUIDService generateUUIDService = mock(GenerateUUIDService.class);
    PersonService personService = new PersonService(personRepository, generateUUIDService);

    @Test
    void testSaveAdmin_ShouldSaveAdminAndReturnPerson() {
        // ARRANGE
        Person person = new Person();
        person.setLoginRole(LoginRole.ADMIN);

        String uuid = "123";
        when(generateUUIDService.getUUID()).thenReturn(uuid);
        when(personRepository.save(person)).thenReturn(person);

        // ACT
        Person result = personService.saveAdmin(person);

        // ASSERT
        verify(generateUUIDService).getUUID();
        verify(personRepository).save(person);

        assertEquals(uuid, person.getPersonId());
        assertEquals(LoginRole.ADMIN, person.getLoginRole());
        assertEquals(person, result);
    }
    @Test
    void testSaveEditor_ShouldSaveUserAndReturnSavedEditor(){
        // ARRANGE
        Person person = new Person();
        person.setLoginRole(LoginRole.EDITOR);

        String uuid = "123";
        when(generateUUIDService.getUUID()).thenReturn(uuid);
        when(personRepository.save(person)).thenReturn(person);

        // ACT
        Person result = personService.saveEditor(person);

        // ASSERT
        verify(generateUUIDService).getUUID();
        verify(personRepository).save(person);

        assertEquals(uuid, person.getPersonId());
        assertEquals(LoginRole.EDITOR, person.getLoginRole());
        assertEquals(person, result);
    }

    @Test
    void testSaveUser_ShouldSaveUserAndReturnSavedUser()  {
        // ARRANGE
        Person person = new Person();
        person.setLoginRole(LoginRole.USER);

        String uuid = "123";
        when(generateUUIDService.getUUID()).thenReturn(uuid);
        when(personRepository.save(person)).thenReturn(person);

        // ACT
        Person result = personService.saveUser(person);

        // ASSERT
        verify(generateUUIDService).getUUID();
        verify(personRepository).save(person);

        assertEquals(uuid, person.getPersonId());
        assertEquals(LoginRole.USER, person.getLoginRole());
        assertEquals(person, result);
    }

    @Test
    void testGetAllPersons_ShouldReturnAllPersons() {
        // ARRANGE
        List<Person> expectedPersons = List.of (new Person(), new Person());
        when(personRepository.findAll()).thenReturn(expectedPersons);

        // ACT
        List<Person> result = personService.getAllPersons();

        // ASSERT
        verify(personRepository).findAll();
        assertEquals(expectedPersons, result);
    }

    @Test
    void testGetPersonsByRole_ShouldReturnPersonsByRole() {
        // ARRANGE
        LoginRole role = LoginRole.ADMIN;
        List<Person> expectedPersons = List.of (new Person(), new Person());
        when(personRepository.findByLoginRoleIs(role)).thenReturn(expectedPersons);

        // ACT
        List<Person> result = personService.getPersonsByRole(role);

        // ASSERT
        verify(personRepository).findByLoginRoleIs(role);
        assertEquals(expectedPersons, result);
    }

    @Test
    void testGetPersonById_ShouldReturnPersonById() {
        // ARRANGE
        String id = "123";
        Person expectedPerson = new Person();

        when(personRepository.findById(id)).thenReturn(Optional.of(expectedPerson));

        // ACT
        Person result = personService.getPersonById(id);

        // ASSERT
        verify(personRepository).findById(id);
        assertEquals(expectedPerson, result);
    }
    @Test
    void testUpdate_ShouldUpdatePersonAndReturnUpdatedPerson() {
        // ARRANGE
        String id = "123";
        Person originalPerson = new Person();
        originalPerson.setPersonId(id);
        originalPerson.setUserName("Klaus Mustermann");

        Person updatedPerson = new Person();
        updatedPerson.setPersonId(id);
        updatedPerson.setUserName("Manu Musterfrau");

        when(personRepository.findById(id)).thenReturn(Optional.of(originalPerson));
        when(personRepository.save(updatedPerson)).thenReturn(updatedPerson);

        // ACT
        Person result = personService.update(updatedPerson, id);

        // ASSERT
        verify(personRepository).findById(id);
        verify(personRepository).save(updatedPerson);
        assertEquals(updatedPerson, result);
    }

    @Test
    void testDelete_ShouldDeletePersonById() {
        // ARRANGE
        String id = "123";

        when(personRepository.findById(id)).thenReturn(Optional.of(new Person()));

        // ACT
        personService.delete(id);

        // ASSERT
        verify(personRepository).findById(id);
        verify(personRepository).deleteById(id);
    }
}