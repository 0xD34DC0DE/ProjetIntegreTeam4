package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String>, CustomStudentRepository {

    Mono<Student> findByEmail(String email);

    Flux<Student> findAllByRole(String role);

    Flux<Student> findAllByRoleAndExclusiveOffersIdNotContains(String role, String offerId);

    Flux<Student> findAllByRoleAndExclusiveOffersIdContains(String role,String offerId);

    Flux<Student> findAllByHasCvFalse();

    Flux<Student> findAllByHasValidCvFalse();

    Flux<Student> findAllByStudentState(StudentState state);

    Flux<Student> findAllByEvaluationsDatesIsBetween(LocalDateTime date1, LocalDateTime date2);

}
