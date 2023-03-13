package com.example.demo.model;

import com.example.demo.dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private Long id = System.currentTimeMillis();
    private String name;
    private int age;

    public Person(PersonDto personDto) {
        this.name = personDto.getName();
        this.age = personDto.getAge();
    }
}
