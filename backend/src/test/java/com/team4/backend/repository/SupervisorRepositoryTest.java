package com.team4.backend.repository;

import com.team4.backend.model.Supervisor;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {SupervisorRepository.class})
public class SupervisorRepositoryTest {

    @Autowired
    SupervisorRepository supervisorRepository;

    @BeforeAll
    void init() {
        Flux<Supervisor> supervisors = Flux.just(Supervisor.supervisorBuilder()
                .email("supervisortest@gmail.com")
                .build());

        supervisorRepository.saveAll(supervisors).blockLast();
    }

    @Test
    void shouldFindSupervisorByEmail() {
        //ARRANGE
        String supervisorEmail = "supervisortest@gmail.com";

        //ACT
        Mono<Supervisor> supervisor = supervisorRepository.findSupervisorByEmail(supervisorEmail);

        //ASSERT
        StepVerifier.create(supervisor)
                .assertNext(s -> assertEquals(supervisorEmail, s.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindSupervisorByEmail() {
        //ARRANGE
        String supervisorEmail = "test_wrong_email@gmail.com";

        //ACT
        Mono<Supervisor> supervisor = supervisorRepository.findSupervisorByEmail(supervisorEmail);

        //ASSERT
        StepVerifier
                .create(supervisor)
                .expectNextCount(0)
                .verifyComplete();
    }

}
