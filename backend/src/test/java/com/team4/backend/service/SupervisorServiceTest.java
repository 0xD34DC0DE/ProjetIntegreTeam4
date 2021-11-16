package com.team4.backend.service;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Semester;
import com.team4.backend.model.Student;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.testdata.EvaluationMockData;
import com.team4.backend.testdata.SemesterMockData;
import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.testdata.SupervisorMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupervisorServiceTest {

    @Mock
    SupervisorRepository supervisorRepository;

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @Mock
    UserService userService;

    @Mock
    StudentService studentService;

    @Mock
    EvaluationService evaluationService;

    @Mock
    SemesterService semesterService;

    @InjectMocks
    SupervisorService supervisorService;


    @Test
    void shouldCreateSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        supervisor.setId(null); // Frontend gives null id

        when(supervisorRepository.save(any(Supervisor.class)))
                .thenReturn(Mono.just(supervisor).map(s -> {
                            s.setId("615a32ce577ae63d7b159b17");
                            return s;
                        }
                ));

        when(pbkdf2Encoder.encode(any(String.class))).thenReturn("base64encryptedtoken");
        when(userService.existsByEmail(supervisor.getEmail())).thenReturn(Mono.just(false));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.registerSupervisor(supervisor);

        //ASSERT
        StepVerifier.create(supervisorMono).consumeNextWith(s -> {
            assertNotNull(s.getId());
            assertNotEquals(SupervisorMockData.getMockSupervisor().getPassword(), s.getPassword());
        }).verifyComplete();
    }

    @Test
    void shouldNotCreateSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        supervisor.setId(null); // Frontend gives null id

        when(userService.existsByEmail(supervisor.getEmail())).thenReturn(Mono.just(true));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.registerSupervisor(supervisor);

        //ASSERT
        StepVerifier.create(supervisorMono).expectError(UserAlreadyExistsException.class).verify();
    }

    @Test
    void shouldAddStudentEmailToList() {
        //ARRANGE
        String studentEmail = "teststudent@gmail.com";
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorRepository.findById(supervisor.getId())).thenReturn(Mono.just(supervisor));
        when(supervisorRepository.save(any(Supervisor.class)))
                .thenReturn(Mono.just(supervisor));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.addStudentEmailToStudentList(supervisor.getId(), studentEmail);

        //ASSERT
        StepVerifier.create(supervisorMono)
                .assertNext(s -> assertEquals(3, s.getStudentTimestampedEntries().size()))
                .verifyComplete();
    }

    @Test
    void shouldNotAddStudentEmailToListWhenAlreadyInTheList() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorRepository.findById(supervisor.getId())).thenReturn(Mono.just(supervisor));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService
                .addStudentEmailToStudentList(supervisor.getId(), "3643283423@gmail.com");

        //ASSERT
        StepVerifier.create(supervisorMono).expectError().verify();
    }

    @Test
    void shouldNotFindSupervisorWhenAddingStudentToList() {
        //ARRANGE
        String studentEmail = "teststudent@gmail.com";
        String wrongId = "wrongId";
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorRepository.findById(wrongId)).thenReturn(Mono.just(supervisor));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.addStudentEmailToStudentList(wrongId, studentEmail);

        //ASSERT
        StepVerifier.create(supervisorMono)
                .expectError()
                .verify();
    }

    @Test
    void shouldGetAssignedStudents() {
        //ARRANGE
        Semester semester = SemesterMockData.getListSemester().get(0);
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        Flux<Student> assignedStudents = StudentMockData.getAssignedStudents();

        when(semesterService.findByFullName(any())).thenReturn(Mono.just(semester));
        when(supervisorRepository.findById(supervisor.getId())).thenReturn(Mono.just(supervisor));
        when(studentService.findAllByEmails(supervisor.getStudentTimestampedEntries().stream()
                .map(TimestampedEntry::getEmail)
                .collect(Collectors.toSet()))).thenReturn(assignedStudents);

        //ACT
        Flux<StudentDetailsDto> studentDetailsDtoFlux = supervisorService.getAllAssignedStudents(supervisor.getId(), semester.getFullName());

        //ASSERT
        StepVerifier.create(studentDetailsDtoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorRepository.findSupervisorByEmail(supervisor.getEmail())).thenReturn(Mono.just(supervisor));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.getSupervisor(supervisor.getEmail());

        //ASSERT
        StepVerifier.create(supervisorMono)
                .assertNext(s -> assertEquals(supervisor.getEmail(), s.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotGetSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorRepository.findSupervisorByEmail(supervisor.getEmail())).thenReturn(Mono.empty());

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.getSupervisor(supervisor.getEmail());

        //ASSERT
        StepVerifier
                .create(supervisorMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldGetAll() {
        //ARRANGE
        when(supervisorRepository.findAllByRole(anyString())).thenReturn(SupervisorMockData.getAllSupervisorsUpdated());

        //ACT
        Flux<Supervisor> response = supervisorService.getAll();

        //ASSERT
        StepVerifier
                .create(response)
                .assertNext(r1 -> {
                    assertEquals(SupervisorMockData.getAllSupervisorsList().get(0).getFirstName(), r1.getFirstName());
                })
                .assertNext(r2 -> {
                    assertEquals(SupervisorMockData.getAllSupervisorsList().get(1).getFirstName(), r2.getFirstName());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetStudentsEmailWithSupervisorWithNoEvaluation() {
        //ARRANGE

        LocalDate localDate0 = LocalDate.parse("2021-01-01");
        LocalDate localDate1 = LocalDate.parse("2021-05-31");

        SupervisorService supervisorServiceSpy = spy(supervisorService);
        doReturn(Mono.just(SupervisorMockData.getAllSupervisorsList())).when(supervisorServiceSpy).getAllWithNoEvaluation(any(), any());

        //ACT
        Mono<List<String>> response = supervisorServiceSpy.getStudentsEmailWithSupervisorWithNoEvaluation(localDate0, localDate1);

        //ASSERT
        StepVerifier.create(response)
                .assertNext(s -> {
                    assertEquals(s.size(), 2);
                })
                .verifyComplete();
    }

    @Test
    void shouldGetAllWithNoEvaluation() {
        //ARRANGE
        LocalDate localDate0 = LocalDate.parse("2021-01-01");
        LocalDate localDate1 = LocalDate.parse("2021-05-31");

        when(evaluationService.getAllWithDateBetween(any(), any())).thenReturn(EvaluationMockData.getAllFlux());

        SupervisorService supervisorServiceSpy = spy(supervisorService);
        doReturn(SupervisorMockData.getAllSupervisorsUpdated()).when(supervisorServiceSpy).getAll();

        //ACT
        Mono<List<Supervisor>> response = supervisorServiceSpy.getAllWithNoEvaluation(localDate0, localDate1);

        //ASSERT
        StepVerifier.create(response)
                .assertNext(s -> {
                    assertEquals(SupervisorMockData.getAllSupervisorsList().get(0).getFirstName(), s.get(0).getFirstName());
                })
                .verifyComplete();
    }

    @Test
    void shouldGetAllWithNoEvaluationOnlyOne() {
        //ARRANGE
        LocalDate localDate0 = LocalDate.parse("2021-01-01");
        LocalDate localDate1 = LocalDate.parse("2021-05-31");

        when(evaluationService.getAllWithDateBetween(any(), any())).thenReturn(EvaluationMockData.getAllFlux2());

        SupervisorService supervisorServiceSpy = spy(supervisorService);
        doReturn(SupervisorMockData.getAllSupervisorsUpdated()).when(supervisorServiceSpy).getAll();

        //ACT
        Mono<List<Supervisor>> response = supervisorServiceSpy.getAllWithNoEvaluation(localDate0, localDate1);

        //ASSERT
        StepVerifier.create(response)
                .assertNext(s -> {
                    assertEquals(SupervisorMockData.getAllSupervisorsList().get(1).getFirstName(), s.get(0).getFirstName());
                })
                .verifyComplete();
    }
}
