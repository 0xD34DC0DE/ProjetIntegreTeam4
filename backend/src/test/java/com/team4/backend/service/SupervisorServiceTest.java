package com.team4.backend.service;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.model.Supervisor;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.testdata.SupervisorMockData;
import com.team4.backend.util.PBKDF2Encoder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
                .assertNext(s -> assertEquals(3, s.getStudentEmails().size()))
                .verifyComplete();
    }

    @Test
    void shouldNotAddStudentEmailToListWhenAlreadyInTheList() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorRepository.findById(supervisor.getId())).thenReturn(Mono.just(supervisor));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService
                .addStudentEmailToStudentList(supervisor.getId(), "toto23@outlook.com");

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
    void shouldGetAssignedStudents(){
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        Flux<Student> assignedStudents  = StudentMockData.getAssignedStudents();

        when(supervisorRepository.findById(supervisor.getId())).thenReturn(Mono.just(supervisor));
        when(studentService.findAllByEmails(supervisor.getStudentEmails())).thenReturn(assignedStudents);

        //ACT
        Flux<StudentDetailsDto> studentDetailsDtoFlux = supervisorService.getAllAssignedStudents(supervisor.getId());

        //ASSERT
        StepVerifier.create(studentDetailsDtoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldGetSupervisor(){
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
    void shouldNotGetSupervisor(){
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorRepository.findSupervisorByEmail(supervisor.getEmail())).thenReturn(Mono.empty());

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.getSupervisor(supervisor.getEmail());

        //ASSERT
        StepVerifier.create(supervisorMono)
                .verifyError(UserNotFoundException.class);
    }
}
