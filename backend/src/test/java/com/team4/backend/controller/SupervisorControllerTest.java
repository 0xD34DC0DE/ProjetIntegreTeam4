package com.team4.backend.controller;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.dto.SupervisorProfileDto;
import com.team4.backend.exception.DuplicateEntryException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.mapping.StudentMapper;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.enums.SemesterName;
import com.team4.backend.service.SupervisorService;
import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.testdata.SupervisorMockData;
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

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@WebFluxTest(value = SupervisorController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class SupervisorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    SupervisorService supervisorService;

    @Test
    public void shouldCreateSupervisor() {
        //ARRANGE
        SupervisorDetailsDto supervisorDto = SupervisorMockData.getMockSupervisorDto();

        supervisorDto.setId(null);

        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorService.registerSupervisor(any(Supervisor.class)))
                .thenReturn(
                        Mono.just(supervisor)
                                .map(s -> {
                                    s.setId("some_id");
                                    return s;
                                })
                );

        //ACT
        webTestClient
                .post().uri("/supervisor/register").bodyValue(supervisorDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    public void shouldNotCreateSupervisor() {
        //ARRANGE
        SupervisorDetailsDto supervisorDto = SupervisorMockData.getMockSupervisorDto();

        supervisorDto.setId(null);

        when(supervisorService.registerSupervisor(any(Supervisor.class)))
                .thenReturn(Mono.error(UserAlreadyExistsException::new));

        //ACT
        webTestClient
                .post().uri("/supervisor/register").bodyValue(supervisorDto)
                .exchange()
                //ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(String.class);
    }

    @Test
    void shouldAssignSupervisorToStudents() {
        //ARRANGE
        String studentEmail = "teststudent@gmail.com";
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorService.addStudentEmailToStudentList(supervisor.getId(), studentEmail))
                .thenReturn(Mono.just(supervisor));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/supervisor/addEmailToStudentList")
                                .queryParam("id", supervisor.getId())
                                .queryParam("studentEmail", studentEmail)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBody(String.class);
    }

    @Test
    void shouldNotAssignSupervisorToStudents() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorService.addStudentEmailToStudentList(supervisor.getId(), "toto23@outlook.com"))
                .thenReturn(Mono.error(DuplicateEntryException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/supervisor/addEmailToStudentList")
                                .queryParam("id", supervisor.getId())
                                .queryParam("studentEmail", "toto23@outlook.com")
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(String.class);
    }

    @Test
    void shouldGetAssignedStudents() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        String semesterFullName = SemesterName.WINTER + "-" + LocalDateTime.now().getYear();

        when(supervisorService.getAllAssignedStudents(supervisor.getId(), semesterFullName))
                .thenReturn(StudentMockData.getAssignedStudents().map(StudentMapper::toDto));

        //ACT
        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/supervisor/getAssignedStudents")
                                .queryParam("supervisorId", supervisor.getId())
                                .queryParam("semesterFullName", semesterFullName)
                                .build())
                .exchange()
                //ASSERT
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBodyList(SupervisorDetailsDto.class);
    }

    @Test
    void shouldGetAllAssignedStudentsForCurrentSemester() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorService.getAllAssignedStudentsForCurrentSemester(supervisor.getId()))
                .thenReturn(StudentMockData.getAssignedStudents().map(StudentMapper::toDto));

        //ACT
        webTestClient
                .get()
                .uri("/supervisor/getAllAssignedStudentsForCurrentSemester/" + supervisor.getId())
                .exchange()
                //ASSERT
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBodyList(SupervisorDetailsDto.class);
    }

    @Test
    void shouldGetSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorService.getSupervisor(supervisor.getEmail())).thenReturn(Mono.just(supervisor));

        //ACT
        webTestClient
                .get()
                .uri("/supervisor/" + supervisor.getEmail())
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(SupervisorDetailsDto.class);
    }

    @Test
    void shouldNotGetSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        when(supervisorService.getSupervisor(any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .get()
                .uri("/supervisor/" + supervisor.getEmail())
                .exchange()
                //ASSERT
                .expectStatus()
                .isNotFound()
                .expectBody(SupervisorDetailsDto.class);
    }

    @Test
    void shouldGetProfile() {
        //ARRANGE
        when(supervisorService.getSupervisor(any())).thenReturn(Mono.just(SupervisorMockData.getMockSupervisor()));

        //ACT
        webTestClient
                .get()
                .uri("/supervisor/getProfile")
                .exchange()
                //ASSERT
                .expectStatus().isOk()
                .expectBody(SupervisorProfileDto.class);
    }

    @Test
    void shouldNotGetProfile() {
        //ARRANGE
        when(supervisorService.getSupervisor(any())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        webTestClient
                .get()
                .uri("/supervisor/getProfile")
                .exchange()
                //ASSERT
                .expectStatus()
                .isNotFound()
                .expectBody(SupervisorProfileDto.class);
    }

}