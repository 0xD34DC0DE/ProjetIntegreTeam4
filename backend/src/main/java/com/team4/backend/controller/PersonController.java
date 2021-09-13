package com.team4.backend.controller;

import com.team4.backend.model.ExamplePerson;
import com.team4.backend.service.PersonService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
@Log
public class PersonController {

    private final PersonService personService;

    @Autowired
    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/personByName/{name}")
    public Mono<ExamplePerson> countByBalance(@PathVariable("name") String name) {
        return personService.findByName(name);
    }

    @GetMapping("/personByNameFirstLetter/{letter}")
    public Flux<ExamplePerson> findAllByNameFirstLetter(@PathVariable("letter") String letter) {
        return personService.findAllByNameFirstLetter(letter);
    }

}
