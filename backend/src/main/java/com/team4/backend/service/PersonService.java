package com.team4.backend.service;

import com.team4.backend.model.ExamplePerson;
import com.team4.backend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

   public Mono<ExamplePerson> findByName(String name) {
        return personRepository.findByName(name);
    }

    public Flux<ExamplePerson> findAllByNameFirstLetter(String letter) {
        return personRepository.findAllByNameFirstLetter(letter);
    }
}
