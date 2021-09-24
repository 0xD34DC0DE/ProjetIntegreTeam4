package com.team4.backend.service;

import com.team4.backend.model.Student;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @Mock
    UserService userService;

    @InjectMocks
    StudentService studentService;

    @Test
    void shouldCreateStudent()
    {
        //ARRANGE

        Student student = StudentMockData.getMockStudent();
        student.setId(null); // Id is null when coming from frontend

        when(studentRepository.save(student))
                .thenReturn(Mono.just(student).map(value -> {
                    value.setId("not_null_id");
                    return value;
                }));

        when(pbkdf2Encoder.encode(any(String.class))).thenReturn("encrypted");

        when(userService.existsByEmail(StudentMockData.getMockStudent().getEmail())).thenReturn(Mono.just(false));

        //ACT

        Mono<Student> studentMono = studentService.registerStudent(student);

        //ASSERT

        StepVerifier.create(studentMono).consumeNextWith(s -> {
            assertNotNull(s.getId());
            assertNotEquals(StudentMockData.getMockStudent().getPassword(), s.getPassword());
        }).verifyComplete();
    }

    @Test
    void shouldNotCreateStudent()
    {
        //ARRANGE

        Student student = StudentMockData.getMockStudent();
        student.setId(null); // Id is null when coming from frontend

        when(userService.existsByEmail(StudentMockData.getMockStudent().getEmail())).thenReturn(Mono.just(true));

        //ACT

        Mono<Student> studentMono = studentService.registerStudent(student);

        //ASSERT

        StepVerifier.create(studentMono).expectNextCount(0).verifyComplete();
    }
}
