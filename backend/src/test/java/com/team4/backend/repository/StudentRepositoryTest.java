package com.team4.backend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void CreateAccount() {

        final String registrationNumber = "123456789";
        final String email = "123456789@gmail.com";
        final String password = "passwd";
        final String firstName = "John";
        final String lastName = "Doe";
        final String schoolName = "Example school";
        final String phoneNumber = "123-456-7890";

        final StudentState studentState = StudentState.Registered;

        Mono<Student> student = Mono.just(
                Student.builder()
                        .registrationNumber(registrationNumber)
                        .email(email)
                        .password(password)
                        .firstName(firstName)
                        .lastName(lastName)
                        .schoolName(schoolName)
                        .phoneNumber(phoneNumber)
                        .isEnabled(true)
                        .build()
        );

        Mono<Student> savedStudent = studentRepository.save(student);

        savedStudent.subscribe(student -> {
            assertNotNull(student.getId());
            assertEquals(student.getrRegistrationNumber(), registrationNumber);
            assertEquals(student.getEmail(), email);
            assertNotEquals(student.password, password); // Password should be encrypted, not cleartext
            assertEquals(student.getFirstName(), firstName);
            assertEquals(student.getLastName(), lastName);
            assertEquals(student.getSchoolName(), schoolName);
            assertEquals(student.getPhoneNumber(), phoneNumber);
            //TODO Add check for isEnable()
        });
    }

}
