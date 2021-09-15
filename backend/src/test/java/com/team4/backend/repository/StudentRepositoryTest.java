package com.team4.backend.repository;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes ={StudentRepository.class})
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

        final StudentState studentState = StudentState.REGISTERED;

        Student student = Student.studentBuilder()
                .registrationNumber(registrationNumber)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .schoolName(schoolName)
                .phoneNumber(phoneNumber)
                .studentState(studentState)
                .build();

        Mono<Student> studentMono = studentRepository.save(student);

        StepVerifier
                .create(studentMono)
                .assertNext(s -> {
                    assertNotNull(s.getId());
                    assertEquals(s.getRegistrationNumber(), registrationNumber);
                    assertEquals(s.getEmail(), email);
                    assertNotEquals(s.getPassword(), password); // Password should be encrypted, not cleartext
                    assertEquals(s.getFirstName(), firstName);
                    assertEquals(s.getLastName(), lastName);
                    assertEquals(s.getSchoolName(), schoolName);
                    assertEquals(s.getPhoneNumber(), phoneNumber);
                    assertEquals(s.getStudentState(), studentState);
                })
                .expectComplete()
                .verify();
        //TODO Add check for isEnable()
    }

}
