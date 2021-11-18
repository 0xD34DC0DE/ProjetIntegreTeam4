package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

public interface CustomStudentRepository {
    Flux<Student> findAllByEmails(Set<String> emails);

    Flux<Student> findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState studentState);

    Flux<Student> findAllByIds(List<String> ids);
}