package com.team4.backend.service;

import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Semester;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import com.team4.backend.repository.StudentRepository;
import com.team4.backend.testdata.SemesterMockData;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @Mock
    UserService userService;

    @Mock
    SemesterService semesterService;

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
    void shouldFindAllByEmails() {
        //ARRANGE
        when(studentRepository.findAllByEmails(anySet())).thenReturn(Flux.fromIterable(StudentMockData.getListStudent(3)));

        //ACT
        Flux<Student> studentFlux = studentService.findAllByEmails(anySet());

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void shouldFindAllByIds() {
        //ARRANGE
        when(studentRepository.findAllByIds(anyList())).thenReturn(Flux.fromIterable(StudentMockData.getListStudent(3)));

        //ACT
        Flux<Student> studentFlux = studentService.findAllByIds(anyList());

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(3)
                .verifyComplete();
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

        student.setHasValidCv(true);
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
        StepVerifier.create(studentMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldNotUpdateStudentStateWhenStudentStateIsNotWaitingForResponse() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        student.setHasValidCv(true);
        student.setStudentState(StudentState.WAITING_INTERVIEW);

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateStudentState(student.getEmail(), StudentState.INTERNSHIP_FOUND);

        //ASSERT
        StepVerifier.create(studentMono)
                .verifyError(ForbiddenActionException.class);
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
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetAllStudentNotContainingExclusiveOffer() {
        //ARRANGE
        String id = "id_test";
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByRoleAndExclusiveOffersIdNotContains("STUDENT", id)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAllStudentNotContainingExclusiveOffer(id);

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetAllStudentContainingExclusiveOffer() {
        //ARRANGE
        String id = "id_test";
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByRoleAndExclusiveOffersIdContains("STUDENT", id)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAllStudentContainingExclusiveOffer(id);

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetAllStudentsWithNoCv() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByHasCvFalse()).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAllStudentsWithNoCv();

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetAllStudentsWithUnvalidatedCv() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByHasValidCvFalse()).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getAllStudentsWithUnvalidatedCv();

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetStudentsNoInternship() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.NO_INTERVIEW)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsNoInterview();

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetStudentsWaitingInterview() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.WAITING_INTERVIEW)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsWaitingInterview();

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetStudentsWaitingResponse() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.WAITING_FOR_RESPONSE)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsWaitingResponse();

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetStudentsWithInternship() {
        //ARRANGE
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(studentRepository.findAllByStudentState(StudentState.INTERNSHIP_FOUND)).thenReturn(students);

        //ACT
        Flux<Student> response = studentService.getStudentsWithInternship();

        //ASSERT
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindById() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findById(anyString())).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.findById(anyString());

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> assertEquals(student.getStudentState(), s.getStudentState()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindById() {
        //ARRANGE
        when(studentRepository.findById(anyString())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<Student> studentMono = studentService.findById(anyString());

        //ACT && ASSERT
        StepVerifier.create(studentMono)
                .verifyError(UserNotFoundException.class);

    }

    @Test
    void shouldGetAllWithNoEvaluationDateDuringSemester() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        Flux<Student> students = StudentMockData.getAllStudentsFlux();

        when(semesterService.findByFullName(semester.getFullName())).thenReturn(Mono.just(semester));
        when(studentRepository.findAllByStudentState(StudentState.INTERNSHIP_FOUND)).thenReturn(students);

        //ACT
        Flux<Student> studentFlux = studentService.getAllWithNoEvaluationDateDuringSemester(semester.getFullName());

        //ASSERT
        StepVerifier.create(studentFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldUpdateInterviewDate() {
        //ARRANGE
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate interviewDate = LocalDate.now().withMonth(Month.AUGUST.getValue())
                .withYear(currentDateTime.getYear())
                .withDayOfMonth(24);
        Student student = StudentMockData.getMockStudent();

        student.setHasValidCv(true);
        student.setStudentState(StudentState.WAITING_INTERVIEW);
        student.setInterviewsDate(new TreeSet<>());

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateInterviewDate(student.getEmail(), interviewDate);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> assertEquals(interviewDate, s.getInterviewsDate().stream().findFirst().get()))
                .verifyComplete();
    }

    @Test
    void shouldUpdateInterviewDateAndSetStudentState() {
        //ARRANGE
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate interviewDate = LocalDate.now().withMonth(Month.AUGUST.getValue())
                .withYear(currentDateTime.getYear())
                .withDayOfMonth(24);
        Student student = StudentMockData.getMockStudent();

        student.setHasValidCv(true);
        student.setStudentState(StudentState.NO_INTERVIEW);
        student.setInterviewsDate(new TreeSet<>());

        when(semesterService.getCurrentSemester()).thenReturn(Mono.just(SemesterMockData.getListSemester().get(0)));
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));
        when(studentRepository.save(any(Student.class))).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateInterviewDate(student.getEmail(), interviewDate);

        //ASSERT
        StepVerifier.create(studentMono)
                .assertNext(s -> {
                    assertEquals(interviewDate, s.getInterviewsDate().stream().findFirst().get());
                    assertEquals(StudentState.WAITING_INTERVIEW, s.getStudentState());
                })
                .verifyComplete();
    }

    @Test
    void shouldNotUpdateInterviewDateWhenNotFound() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.empty());

        //ACT
        Mono<Student> studentMono = studentService.updateInterviewDate(student.getEmail(), any());

        //ASSERT
        StepVerifier.create(studentMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldNotUpdateInterviewDateWhenStudentStateIsInternshipFound() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        student.setHasValidCv(true);
        student.setStudentState(StudentState.INTERNSHIP_FOUND);

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Mono.just(student));

        //ACT
        Mono<Student> studentMono = studentService.updateInterviewDate(student.getEmail(), any());

        //ASSERT
        StepVerifier.create(studentMono)
                .verifyError(ForbiddenActionException.class);
    }

    @Test
    void shouldUpdateStudentStateForAllStudentThatInterviewDateHasPassed() {
        //ARRANGE
        List<Student> students = StudentMockData.getAllStudentsToUpdate();

        when(studentRepository.findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState.WAITING_INTERVIEW))
                .thenReturn(Flux.fromIterable(students));

        students.forEach(student -> when(studentRepository.save(student)).thenReturn(Mono.just(student)));

        //ACT
        Mono<Long> nbrOfUpdatedStudent = studentService.updateStudentStateForAllStudentThatInterviewDateHasPassed();

        //ASSERT
        StepVerifier.create(nbrOfUpdatedStudent)
                .assertNext(n -> assertEquals(2, n))
                .verifyComplete();

    }

    @Test
    void shouldNotUpdateStudentStateForAllStudentThatInterviewDateHasPassed() {
        //ARRANGE
        when(studentRepository.findAllByStudentStateAndInterviewsDateIsNotEmpty(StudentState.WAITING_INTERVIEW))
                .thenReturn(Flux.empty());

        //ACT
        Mono<Long> nbrOfUpdatedStudent = studentService.updateStudentStateForAllStudentThatInterviewDateHasPassed();

        //ASSERT
        StepVerifier.create(nbrOfUpdatedStudent)
                .assertNext(n -> assertEquals(0, n))
                .verifyComplete();
    }

    @Test
    void shouldResetStudentStateForAllStudentWithInternship() {
        //ARRANGE
        List<Student> students = StudentMockData.getAllStudentsWithInternshipFound();

        when(studentRepository.findAllByStudentState(StudentState.INTERNSHIP_FOUND)).thenReturn(Flux.fromIterable(students));
        students.forEach(student -> when(studentRepository.save(student)).thenReturn(Mono.just(student)));

        //ACT
        Mono<Long> nbrOfStudentReset = studentService.resetStudentStateForAllStudentWithInternship();

        //ASSERT
        StepVerifier.create(nbrOfStudentReset)
                .assertNext(n -> assertEquals(2, n))
                .verifyComplete();
    }

    @Test
    void shouldNotResetStudentStateForAllStudentWithInternship() {
        //ARRANGE
        when(studentRepository.findAllByStudentState(StudentState.INTERNSHIP_FOUND)).thenReturn(Flux.empty());

        //ACT
        Mono<Long> nbrOfStudentReset = studentService.resetStudentStateForAllStudentWithInternship();

        //ASSERT
        StepVerifier.create(nbrOfStudentReset)
                .assertNext(n -> assertEquals(0, n))
                .verifyComplete();
    }

}
