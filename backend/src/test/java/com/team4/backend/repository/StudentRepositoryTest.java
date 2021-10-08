package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import org.junit.jupiter.api.Assertions;
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

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {StudentRepository.class})
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeAll
    void init() {
        Flux<Student> users = Flux.just(
                Student.studentBuilder().email("testing_1@gmail.com").password("password1").build(),
                Student.studentBuilder().email("testing_2@gmail.com").password("password2").build()
        );

        studentRepository.saveAll(users).subscribe();
    }

    @Test
    void shouldFindByEmail(){
        //ARRANGE
        String email = "testing_1@gmail.com";

        //ACT
        Mono<Student> studentMono = studentRepository.findByEmail(email);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> Assertions.assertEquals(email,s.getEmail()))
                .verifyComplete();

    }

    @Test
    void shouldNotFindByEmail(){
        //ARRANGE
        String email = "non_existent_student@gmail.com";

        //ACT
        Mono<Student> studentMono = studentRepository.findByEmail(email);

        //ASSERT
        StepVerifier.create(studentMono)
                .expectNextCount(0)
                .verifyComplete();
    }

}
