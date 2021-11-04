package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
                Student.studentBuilder()
                        .email("testing_1@gmail.com")
                        .password("password1")
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .interviewsDate(new TreeSet<>(Arrays.asList(
                                LocalDate.now().minusWeeks(3),
                                LocalDate.now(),
                                LocalDate.now().minusDays(5))))
                        .build(),
                Student.studentBuilder()
                        .email("testing_2@gmail.com")
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .password("password2")
                        .interviewsDate(new TreeSet<>())
                        .build(),
                Student.studentBuilder()
                        .email("testing_3@gmail.com")
                        .password("password1")
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .interviewsDate(new TreeSet<>(Arrays.asList(
                                LocalDate.now().minusWeeks(3),
                                LocalDate.now(),
                                LocalDate.now().minusDays(5))))
                        .build()
        );

        studentRepository.saveAll(users).subscribe();
    }

    @Test
    void shouldFindByEmail() {
        //ARRANGE
        String email = "testing_1@gmail.com";

        //ACT
        Mono<Student> studentMono = studentRepository.findByEmail(email);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> Assertions.assertEquals(email, s.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindByEmail() {
        //ARRANGE
        String email = "non_existent_student@gmail.com";

        //ACT
        Mono<Student> studentMono = studentRepository.findByEmail(email);

        //ASSERT
        StepVerifier.create(studentMono)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldFindAllByEmails() {
        //ARRANGE
        Set<String> emails = new HashSet<>(Arrays.asList("testing_1@gmail.com", "testing_2@gmail.com"));

        //ACT
        Flux<Student> studentFlux = studentRepository.findAllByEmails(emails);

        //ASSERT
        StepVerifier.create(studentFlux)
                .assertNext(s -> Assertions.assertNotNull(s.getEmail()))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldNotFindAllByEmails() {
        //ARRANGE
        Set<String> emails = new HashSet<>(Arrays.asList("nonexistentemail1@gmail.com", "nonexistentemail2@gmail.com"));

        //ACT
        Flux<Student> studentFlux = studentRepository.findAllByEmails(emails);

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldFindAllByStudentStateAndInterviewsDateIsNotEmpty() {
        //ARRANGE && ACT
        Flux<Student> studentFlux = studentRepository.findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState.INTERNSHIP_NOT_FOUND);

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

}
