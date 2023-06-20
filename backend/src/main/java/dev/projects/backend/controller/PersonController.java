package dev.projects.backend.controller;

import de.neuefische.backend.collection.Person;
import de.neuefische.backend.dto.PersonDTO;
import de.neuefische.backend.enums.LoginRole;
import de.neuefische.backend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    //CREATE
    @PostMapping("/person/master-admin")
    public ResponseEntity<String> checkAndCreateMasterAdmin(){
        List<Person> personList = getPersonsByRole(LoginRole.ADMIN);
        if(personList.size()!=0) {
            for (int i=0; i<personList.size(); i++){
                if(personList.get(i).getUserName().equals("${ENV_UN_Admin}")){
                    return ResponseEntity.ok("MasterAdmin already exists.");
                }
            }
        }
        PersonDTO masterAdmin = new PersonDTO();
        masterAdmin.setUserName("${ENV_UN_Admin}");
        masterAdmin.setPassWord("${ENV_PW_Admin}");
        masterAdmin.setLoginRole(LoginRole.ADMIN);
        saveAdmin(masterAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body("MasterAdmin has been successfully created.");
    }
    @PostMapping("/person/admin")
    //@PreAuthorize("hasAuthority('LoginRole=ADMIN')")
    public Person saveAdmin(@RequestBody PersonDTO person){
        Person addAdmin = Person.personBuilder()
                .userName(person.getUserName())
                .passWord(person.getPassWord())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .contactDetails(person.getContactDetails())
                .addresses(person.getAddresses())
                .loginRole(person.getLoginRole())
                .gpsData(person.getGpsData())
                .build();
        return personService.saveAdmin(addAdmin);
    }
    @PostMapping("/person/editor")
    //@PreAuthorize("hasAuthority('LoginRole=ADMIN')")
    public Person saveEditor(@RequestBody PersonDTO  person){
        Person addEditor = Person.personBuilder()
                .userName(person.getUserName())
                .passWord(person.getPassWord())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .contactDetails(person.getContactDetails())
                .addresses(person.getAddresses())
                .loginRole(person.getLoginRole())
                .gpsData(person.getGpsData())
                .build();
        return personService.saveEditor(addEditor);
    }
    @PostMapping("/person/user")
    //@PreAuthorize("hasAuthority('LoginRole=ADMIN')")
    public Person saveUser(@RequestBody PersonDTO person){
        Person addUser = Person.personBuilder()
                .userName(person.getUserName())
                .passWord(person.getPassWord())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .contactDetails(person.getContactDetails())
                .addresses(person.getAddresses())
                .loginRole(person.getLoginRole())
                .gpsData(person.getGpsData())
                .build();
        return personService.saveUser(addUser);
    }

    //READ
    @GetMapping("/persons")
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }
    @GetMapping("/persons/role")
    public List<Person> getPersonsByRole(@RequestParam("role") LoginRole role){
        return personService.getPersonsByRole(role);
    }

    @GetMapping("/person/{id}")
    public Person getPersonById(@PathVariable String id){
        return personService.getPersonById(id);
    }

    //UPDATE
    @PutMapping("/person/{id}")
    public Person update(@RequestBody PersonDTO person, @PathVariable String id){
        Person updatePerson = Person.personBuilder()
                .personId(id)
                .userName(person.getUserName())
                .passWord(person.getPassWord())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .contactDetails(person.getContactDetails())
                .addresses(person.getAddresses())
                .loginRole(person.getLoginRole())
                .gpsData(person.getGpsData())
                .build();
        return personService.update(updatePerson, id);
    }
    //DELETE
    @DeleteMapping("/person/{id}")
    public void delete(@PathVariable String id){
        personService.delete(id);
    }
}
