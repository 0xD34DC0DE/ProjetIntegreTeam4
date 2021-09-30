package com.team4.backend.service;

import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @Mock
    MonitorRepository monitorRepository;

    @InjectMocks
    MonitorService monitorService;

    @Test
    void findMonitorByEmailExists() {
        //ARRANGE
        String email = "marcM@desjardin.com";
        Mono<Monitor> existingMonitor = Mono.just(Monitor.monitorBuilder().email(email).build());

        when(monitorRepository.findByEmail(any(String.class))).thenReturn(existingMonitor);

        //ACT
        Mono<Monitor> monitorMono = monitorService.findMonitorByEmail(email);

        //ASSERT
        StepVerifier.create(monitorMono)
                .assertNext(monitor -> assertEquals(email, monitor.getEmail()))
                .verifyComplete();
    }

    @Test
    void findMonitorByEmailDoesNotExists() {
        //ARRANGE
        String email = "inexistantEmail@gmail.com";

        when(monitorRepository.findByEmail(any(String.class))).thenReturn(Mono.empty());

        //ACT
        Mono<Monitor> monitorMono = monitorService.findMonitorByEmail(email);

        //ASSERT
        StepVerifier.create(monitorMono)
                .verifyError(ResponseStatusException.class);
    }
}
