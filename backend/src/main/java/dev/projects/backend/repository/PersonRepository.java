package dev.projects.backend.repository;

import dev.projects.backend.collection.Person;
import dev.projects.backend.enums.LoginRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person, String> {
    List<Person> findByLoginRoleIs(LoginRole role);
}
