package com.example.demo.controllers;

import com.example.demo.dao.PersonDao;
import com.example.demo.dto.PersonDto;
import com.example.demo.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() throws SQLException {
        return PersonDao.getPersons().stream().map(person -> new PersonDto(person)).collect(Collectors.toList());
    }

    @PostMapping("/createTable")
    public ResponseEntity<String> createTable(@RequestParam String name) throws SQLException {
        PersonDao.createTable(name);
        return ResponseEntity.ok("Successfully created");
    }

    @PostMapping("/person")
    public void createPerson(@RequestBody PersonDto person) throws SQLException {
        Person newPerson = new Person(person);
        PersonDao.insert(newPerson);
    }
}
