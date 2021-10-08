package com.team4.backend.controller;

import com.team4.backend.dto.SupervisorDto;
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
        SupervisorDto supervisorDto = SupervisorMockData.getMockSupervisorDto();

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
        SupervisorDto supervisorDto = SupervisorMockData.getMockSupervisorDto();

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

}
