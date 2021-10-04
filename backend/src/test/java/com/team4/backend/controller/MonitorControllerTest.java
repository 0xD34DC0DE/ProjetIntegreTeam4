package com.team4.backend.controller;

import com.team4.backend.dto.MonitorDto;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Monitor;
import com.team4.backend.service.MonitorService;
import com.team4.backend.service.UserService;
import com.team4.backend.testdata.MonitorMockData;
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
@WebFluxTest(value = MonitorController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class MonitorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    MonitorService monitorService;

    @MockBean
    UserService userService;

    @Test
    public void shouldCreateMonitor() {
        //ARRANGE
        MonitorDto monitorDto = MonitorMockData.getMockMonitorDto();

        monitorDto.setId(null); // Frontend gives null id

        Monitor monitor = MonitorMockData.getMockMonitor();

        when(monitorService.registerMonitor(any(Monitor.class)))
                .thenReturn(
                        Mono.just(monitor)
                                .map(m -> {
                                    m.setId("615259e03835be1f53bd49e4");
                                    return m;
                                })
                );

        //ACT
        webTestClient
                .post().uri("/monitor/register").bodyValue(monitorDto)
                .exchange()
                //ASSERT
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    @Test
    public void shouldNotCreateMonitor() {
        //ARRANGE
        MonitorDto monitorDto = MonitorMockData.getMockMonitorDto();

        monitorDto.setId(null); // Frontend gives null id

        when(monitorService.registerMonitor(any(Monitor.class)))
                .thenReturn(Mono.error(new UserAlreadyExistsException()));

        //ACT
        webTestClient
                .post().uri("/monitor/register").bodyValue(monitorDto)
                .exchange()
                // ASSERT
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody().isEmpty();

    }

}
