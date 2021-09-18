package com.team4.backend.service;

import com.team4.backend.model.Student;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.util.PBKDF2Encoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final PBKDF2Encoder pbkdf2Encoder;

    public StudentService(StudentRepository studentRepository, PBKDF2Encoder pbkdf2Encoder) {
        this.studentRepository = studentRepository;
        this.pbkdf2Encoder = pbkdf2Encoder;
    }

    public Mono<Student> registerStudent(Student student) {
        student.setPassword(pbkdf2Encoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

}
