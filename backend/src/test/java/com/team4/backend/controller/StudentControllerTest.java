package com.team4.backend.controller;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.StudentProfileDto;
import com.team4.backend.dto.UserDto;
import com.team4.backend.exception.ForbiddenActionException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.Student;
import com.team4.backend.service.StudentService;
import com.team4.backend.service.SupervisorService;
import com.team4.backend.testdata.StudentMockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = StudentController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    StudentService studentService;

    @MockBean
    SupervisorService supervisorService;

    @Test
    public void shouldCreateStudent() {
        //ARRANGE
        StudentDetailsDto studentCreationDto = StudentMockData.getMockStudentDto();

        studentCreationDto.setId(null);

        Student student = StudentMockData.getMockStudent();

        when(studentService.registerStudent(any(Student.class)))
                .thenReturn(
                        Mono.just(student)
                                .map(s -> {
                                    s.setId("some_id");
                                    return s;
                                })
                );

        //ACT
        webTestClient
                .post().uri("/student/register").bodyValue(studentCreationDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    public void shouldNotCreateStudent() {
        //ARRANGE
        StudentDetailsDto studentCreationDto = StudentMockData.getMockStudentDto();

        studentCreationDto.setId(null);

        when(studentService.registerStudent(any(Student.class)))
                .thenReturn(Mono.error(UserAlreadyExistsException::new));

        //ACT
        webTestClient
                .post().uri("/student/register").bodyValue(studentCreationDto)
                .exchange()
                //ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(String.class);
    }

    @Test
    void shouldUpdateStudentState() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentService.updateStudentState(any(), any())).thenReturn(Mono.just(student));

        //ACT
        webTestClient
                .patch()
                .uri("/student/updateStudentState")
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBodyList(String.class);
    }

    @Test
    void shouldNotUpdateStudentStateWhenNotFound() {
        //ARRANGE
        when(studentService.updateStudentState(any(), any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .patch()
                .uri("/student/updateStudentState")
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBodyList(String.class);
    }

    @Test
    void shouldNotUpdateStudentStateWhenStudentStateIsNotWaitingForResponse() {
        //ARRANGE
        when(studentService.updateStudentState(any(), any())).thenReturn(Mono.error(ForbiddenActionException::new));

        //ACT
        webTestClient
                .patch()
                .uri("/student/updateStudentState")
                .exchange()
                //ASSERT
                .expectStatus().isForbidden()
                .expectBodyList(String.class);
    }

    @Test
    void shouldUpdateInterviewDate() {
        //ARRANGE
        LocalDate interviewDate = LocalDate.now();
        Student student = StudentMockData.getMockStudent();

        when(studentService.updateInterviewDate(any(), any())).thenReturn(Mono.just(student));

        //ACT
        webTestClient
                .patch()
                .uri("/student/updateInterviewDate/" + interviewDate)
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBodyList(String.class);
    }

    @Test
    void shouldNotUpdateInterviewDateWhenNotFound() {
        //ARRANGE
        LocalDate interviewDate = LocalDate.now();
        when(studentService.updateInterviewDate(any(), any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .patch()
                .uri("/student/updateInterviewDate/" + interviewDate)
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBodyList(String.class);
    }

    @Test
    void shouldNotUpdateInterviewDateWhenStudentStateIsInternshipFound() {
        //ARRANGE
        LocalDate interviewDate = LocalDate.now();
        when(studentService.updateInterviewDate(any(), any())).thenReturn(Mono.error(ForbiddenActionException::new));

        //ACT
        webTestClient
                .patch()
                .uri("/student/updateInterviewDate/" + interviewDate)
                .exchange()
                //ASSERT
                .expectStatus().isForbidden()
                .expectBodyList(String.class);
    }

    @Test
    void shouldGetStudentProfile() {
        //ARRANGE
        Student student = StudentMockData.getMockStudent();

        when(studentService.findByEmail(any())).thenReturn(Mono.just(student));

        //ACT
        webTestClient
                .get()
                .uri("/student/getProfile")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(StudentProfileDto.class);
    }

    @Test
    void shouldNotGetStudentProfile() {
        //ARRANGE
        when(studentService.findByEmail(any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .get()
                .uri("/student/getProfile")
                .exchange()
                //ASSERT
                .expectStatus().isNotFound()
                .expectBodyList(StudentProfileDto.class);
    }

    @Test
    void shouldGetAllStudentsNoSupervisor() {
        //ARRANGE
        when(supervisorService.getAllStudentsNoSupervisor()).thenReturn(StudentMockData.getAllFluxUserDto());

        //ACT
        webTestClient
                .get()
                .uri("/student/getAllStudentsNoSupervisor")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBodyList(UserDto.class);
    }

}
