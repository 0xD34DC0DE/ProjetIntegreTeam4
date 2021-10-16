package com.team4.backend.repository;

import com.team4.backend.model.Student;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CustomStudentRepository {
    Flux<Student> findAllByEmails(List<String> emails);
}