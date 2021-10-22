package com.team4.backend.controller;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.exception.DuplicateEntryException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Supervisor;
import com.team4.backend.service.SupervisorService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public void shouldNotSupervisor() {
        //ARRANGE
        SupervisorDetailsDto supervisorDto = SupervisorMockData.getMockSupervisorDto();

        supervisorDto.setId(null);

        when(supervisorService.registerSupervisor(any(Supervisor.class)))
                .thenReturn(Mono.error(UserAlreadyExistsException::new));

        //ACT
        webTestClient
                .post().uri("/supervisor/register").bodyValue(supervisorDto)
                .exchange()
                // ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody().isEmpty();
    }

    @Test
    void shouldAssignSupervisorToStudents(){
        // ARRANGE
        String studentEmail = "teststudent@gmail.com";
        SupervisorDetailsDto supervisorDto = SupervisorMockData.getMockSupervisorDto();
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();

        when(supervisorService.addStudentEmailToStudentList(supervisorDto.getId(), studentEmail))
            .thenReturn(Mono.just(supervisor));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/supervisor/addEmailToStudentList")
                                .queryParam("id", supervisorDto.getId())
                                .queryParam("studentEmail", studentEmail)
                                .build())
                .bodyValue(supervisorDto)
                .exchange()
                //ASSERT
                .expectStatus().isNoContent()
                .expectBody(String.class);
    }

    @Test
    void shouldNotAssignSupervisorToStudents(){
        // ARRANGE
        SupervisorDetailsDto supervisorDto = SupervisorMockData.getMockSupervisorDto();

        when(supervisorService.addStudentEmailToStudentList(supervisorDto.getId(), "toto23@outlook.com"))
                .thenReturn(Mono.error(DuplicateEntryException::new));

        //ACT
        webTestClient
                .patch()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/supervisor/addEmailToStudentList")
                                .queryParam("id", supervisorDto.getId())
                                .queryParam("studentEmail", "toto23@outlook.com")
                                .build())
                .bodyValue(supervisorDto)
                .exchange()
                //ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(String.class);
    }

    @Test
    void shouldGetAllSupervisors(){
        //ARRANGE
        when(supervisorService.getAllSupervisors()).thenReturn(SupervisorMockData.getAllSupervisors());

        //ACT
        webTestClient.get().uri(uriBuilder ->
                uriBuilder
                    .path("/supervisor/getAll")
                    .build())
                .exchange()
        //ASSERT
                .expectStatus().isOk()
                .expectBodyList(SupervisorDetailsDto.class);

    }
}
