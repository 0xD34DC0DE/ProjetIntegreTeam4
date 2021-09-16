package com.team4.backend.service;

import com.team4.backend.dto.StudentDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    void shouldCreateUser()
    {
        //ARRANGE
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

        when(studentRepository.save(student)).thenReturn(Mono.just(student).map(s -> {s.setId("aaaaa"); return s;}));

        //ACT

        Mono<Student> studentMono = studentService.registerStudent(student);

        //ASSERT

        StepVerifier.create(studentMono).consumeNextWith(s -> {
            assertNotNull(s.getId());
            assertNull(s.getPassword()); // Don't give password back to frontend
            assertEquals(s.getRegistrationNumber(), registrationNumber);
            assertEquals(s.getEmail(), email);
            assertEquals(s.getFirstName(), firstName);
            assertEquals(s.getLastName(), lastName);
            assertEquals(s.getSchoolName(), schoolName);
            assertEquals(s.getPhoneNumber(), phoneNumber);
            assertEquals(s.getStudentState(), studentState);
        }).verifyComplete();

    }
}
