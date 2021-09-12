package com.team4.backend.repository;

import com.team4.backend.model.ExamplePerson;
import reactor.core.publisher.Flux;


public interface CustomPersonRepository {
    Flux<ExamplePerson> findAllByNameFirstLetter(String letter);
}
