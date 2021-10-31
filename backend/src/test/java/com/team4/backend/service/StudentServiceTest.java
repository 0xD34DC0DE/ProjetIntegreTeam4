package com.team4.backend.service;

import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
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
    void shouldAddAppliedOffer() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.save(any(Student.class))).then(s -> {
            student.getAppliedOffersId().add("offerId");
            return Mono.just(student);
        });

        //ACT
        Mono<Student> studentMono = studentService.addOfferToStudentAppliedOffers(student, "offerId");

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> {
                    assertEquals(1, student.getAppliedOffersId().size());
                    assertTrue(student.getAppliedOffersId().contains("offerId"));
                })
                .verifyComplete();
    }


    @Test
    void shouldNotAddAppliedOffer() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();
        student.getAppliedOffersId().add("offerId");

        //ACT
        Mono<Student> studentMono = studentService.addOfferToStudentAppliedOffers(student, "offerId");

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> {
                    assertEquals(1, student.getAppliedOffersId().size());
                    assertTrue(student.getAppliedOffersId().contains("offerId"));
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateStudentState() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        student.setStudentState(StudentState.WAITING_FOR_RESPONSE);

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateStudentState(student.getEmail(), StudentState.INTERNSHIP_FOUND);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> assertEquals(StudentState.INTERNSHIP_FOUND, s.getStudentState()))
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

    @Test
    void shouldSetHasCvStatusTrue() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(any(String.class))).thenReturn(Mono.just(student));
        when(studentRepository.save(any())).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> response = studentService.setHasCvStatusTrue(student.getEmail());

        //ASSERT
        StepVerifier.create(response).consumeNextWith(s -> {
            assertEquals(student, s);
        }).verifyComplete();
    }

    @Test
    void shouldGetAll() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByRole("STUDENT")).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAll();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetAllStudentsWithNoCv() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByHasCvFalse()).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAllStudentsWithNoCv();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetAllStudentsWithUnvalidatedCv() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByHasValidCvFalse()).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAllStudentsWithUnvalidatedCv();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetStudentsNoInternship() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.REGISTERED)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsNoInternship();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetStudentsWaitingInterview() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.INTERNSHIP_NOT_FOUND)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsWaitingInterview();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetStudentsWaitingResponse() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.WAITING_FOR_RESPONSE)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsWaitingResponse();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }

    @Test
    void shouldGetStudentsWithInternship() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.INTERNSHIP_FOUND)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsWithInternship();

        //ASSERT
        StepVerifier.create(response).assertNext(s -> {
            assertEquals(StudentMockData.getMockStudent(), s);
        }).assertNext(s -> {
            assertEquals(StudentMockData.getMockSecondStudent(), s);
        }).verifyComplete();
    }
}
