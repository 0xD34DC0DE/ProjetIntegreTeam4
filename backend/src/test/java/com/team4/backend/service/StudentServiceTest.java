package com.team4.backend.service;

import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.UnauthorizedException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

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
    void shouldCreateStudent() {
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
    void shouldNotCreateStudent() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        student.setId(null); // Id is null when coming from frontend

        when(userService.existsByEmail(StudentMockData.getMockStudent().getEmail())).thenReturn(Mono.just(true));

        //ACT
        Mono<Student> studentMono = studentService.registerStudent(student);

        //ASSERT
        StepVerifier.create(studentMono).expectError(UserAlreadyExistsException.class).verify();
    }

    @Test
    void shouldFindByEmail() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.findByEmail(student.getEmail());

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> assertEquals(student.getEmail(), s.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindByEmail() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.empty());

        //ACT
        Mono<Student> studentMono = studentService.findByEmail(student.getEmail());

        //ASSERT
        StepVerifier.create(studentMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldUpdateCvValidity() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateCvValidity(student.getEmail(), true);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> assertTrue(s.getHasValidCv()))
                .verifyComplete();
    }

    @Test
    void shouldNotUpdateCvValidity() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.empty());

        //ACT
        Mono<Student> studentMono = studentService.updateCvValidity(student.getEmail(), true);

        //ASSERT
        StepVerifier.create(studentMono).verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldFindStudent() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmailAndIsEnabledTrue(same(student.getEmail()))).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.getStudent(student.getEmail());

        //ASSERT
        StepVerifier.create(studentMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldNotFindStudent() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmailAndIsEnabledTrue(same(student.getEmail()))).thenReturn(Mono.empty());

        //ACT
        Mono<Student> studentMono = studentService.getStudent(student.getEmail());

        //ASSERT
        StepVerifier.create(studentMono)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldUpdateStudentState() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateStudentState(student.getEmail(), StudentState.INTERNSHIP_FOUND);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> assertEquals(StudentState.INTERNSHIP_FOUND,s.getStudentState()))
                .verifyComplete();
    }

    @Test
    void shouldNotUpdateStudentStateWhenNotFound() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.empty());

        //ACT
        Mono<Student> studentMono = studentService.updateStudentState(student.getEmail(), StudentState.INTERNSHIP_FOUND);

        //ASSERT
        StepVerifier.create(studentMono).verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldNotUpdateStudentStateWhenStudentStateIsNotWaitingForResponse() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        student.setStudentState(StudentState.INTERNSHIP_NOT_FOUND);

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateStudentState(student.getEmail(), StudentState.INTERNSHIP_FOUND);

        //ASSERT
        StepVerifier.create(studentMono).verifyError(ForbiddenActionException.class);
    }

}
