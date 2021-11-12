package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String>, CustomStudentRepository {
    Mono<Student> findByEmailAndIsEnabledTrue(String studentEmail);

    Mono<Student> findByEmail(String email);

    Flux<Student> findAllByRole(String role);

    Flux<Student> findAllByHasCvFalse();

    Flux<Student> findAllByHasValidCvFalse();

    Flux<Student> findAllByStudentState(StudentState state);

    Flux<Student> findAllByEvaluationsDatesIsBetween(LocalDate sessionStart, LocalDate sessionEnd);

}
