package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.Role;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .exclusiveOffersId(Set.of("61a679bf094df9767f779234"))
                        .interviewsDate(new TreeSet<>(Arrays.asList(
                                LocalDate.now().minusWeeks(3),
                                LocalDate.now(),
                                LocalDate.now().minusDays(5))))
                        .build(),
                Student.studentBuilder()
                        .email("testing_2@gmail.com")
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .password("password2")
                        .exclusiveOffersId(new HashSet<>())
                        .interviewsDate(new TreeSet<>())
                        .evaluationsDates(Stream.of(LocalDate.now(), LocalDate.now().plusYears(2))
                                .collect(Collectors.toCollection(TreeSet::new)))
                        .build(),
                Student.studentBuilder()
                        .email("testing_3@gmail.com")
                        .password("password1")
                        .exclusiveOffersId(new HashSet<>())
                        .studentState(StudentState.WAITING_INTERVIEW)
                        .evaluationsDates(Stream.of(LocalDate.now().plusYears(2))
                                .collect(Collectors.toCollection(TreeSet::new)))
                        .interviewsDate(new TreeSet<>(Arrays.asList(
                                LocalDate.now().minusWeeks(3),
                                LocalDate.now(),
                                LocalDate.now().minusDays(5))))
                        .build()
        );

        studentRepository.saveAll(users).blockLast();
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
    void shouldFindAllByRoleAndExclusiveOffersIdNotContains() {
        //ARRANGE
        String id = "61a679bf094df9767f779234";
        String role = Role.STUDENT.toString();


        //ACT
        Flux<Student> studentFlux = studentRepository.findAllByRoleAndExclusiveOffersIdNotContains(role, id);

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldFindAllByRoleAndExclusiveOffersIdContains() {
        //ARRANGE
        String id = "61a679bf094df9767f779234";
        String role = Role.STUDENT.toString();


        //ACT
        Flux<Student> studentFlux = studentRepository.findAllByRoleAndExclusiveOffersIdContains(role, id);

        //ASSERT
        StepVerifier.create(studentFlux)
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
        Flux<Student> studentFlux = studentRepository.findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState.WAITING_INTERVIEW);

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindAllByEvaluationsDatesIsBetween() {
        //ARRANGE
        LocalDateTime currentDateTime = LocalDateTime.now();

        //ACT
        Flux<Student> studentFlux = studentRepository.findAllByEvaluationsDatesIsBetween(currentDateTime.minusDays(3), currentDateTime.plusWeeks(2));

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

}
