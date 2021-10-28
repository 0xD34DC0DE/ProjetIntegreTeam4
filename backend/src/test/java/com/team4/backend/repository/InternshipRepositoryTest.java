package com.team4.backend.repository;

import com.team4.backend.model.Internship;
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

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {InternshipRepository.class})
public class InternshipRepositoryTest {

    @Autowired
    private InternshipRepository internshipRepository;

    @BeforeAll
    void init(){
        Flux<Internship> internshipFlux = Flux.just(Internship
                .builder()
                .studentEmail("test_student@gmail.com")
                .build(),
                Internship
                        .builder()
                        .studentEmail("test_student@gmail.com")
                        .build());

        internshipRepository.saveAll(internshipFlux).subscribe();
    }

    @Test
    void shouldGetAllInternshipByStudentEmail(){
        //ARRANGE
        String studentEmail = "test_student@gmail.com";

        //ACT
        Mono<Internship> internshipMono = internshipRepository.findInternshipByStudentEmail(studentEmail);

        //ASSERT
        StepVerifier
                .create(internshipMono)
                .assertNext(i -> assertEquals(studentEmail, i.getStudentEmail()))
                .verifyComplete();
    }

}
