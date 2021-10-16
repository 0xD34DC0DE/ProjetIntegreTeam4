package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    private final UserService userService;

    public StudentService(StudentRepository studentRepository, PBKDF2Encoder pbkdf2Encoder, UserService userService) {
        this.studentRepository = studentRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
        this.userService = userService;
    }

    public Mono<Student> registerStudent(Student student) {
        return userService.existsByEmail(student.getEmail()).flatMap(exists -> {
            if (!exists) {
                student.setPassword(pbkdf2Encoder.encode(student.getPassword()));
                return studentRepository.save(student);
            } else {
                return Mono.error(new UserAlreadyExistsException("User already exist"));
            }
        });
    }

    public Mono<Student> getStudent(String studentEmail) {
        return studentRepository.findByEmailAndIsEnabledTrue(studentEmail);
    }

    public Mono<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Can't find user with this email")));
    }

    public Flux<Student> findAllByEmail(List<String> emails) {
        return studentRepository.findAllByEmails(emails);
    }

    public Mono<Student> updateCvValidity(String email, Boolean valid) {
        return findByEmail(email).map(student -> {
            student.setHasValidCv(valid);
            return student;
        }).flatMap(studentRepository::save);
    }

}
