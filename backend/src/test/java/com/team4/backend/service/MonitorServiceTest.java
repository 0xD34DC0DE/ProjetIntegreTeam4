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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @Mock
    MonitorRepository monitorRepository;

    @InjectMocks
    MonitorService monitorService;

    @Test
    void findMonitorByEmail() {
        //ARRANGE

        String email1 = "marcM@desjardin.com";
        Mono<Monitor> existingMonitor = Mono.just(Monitor.monitorBuilder().email(email1).build());
        when(monitorRepository.findByEmail(email1)).thenReturn(existingMonitor);

        String email2 = "inexistantEmail@gmail.com";
        when(monitorRepository.findByEmail(email2)).thenReturn(Mono.empty());

        //ACT
        Mono<Monitor> returnedMonitor = monitorService.findMonitorByEmail(email1);
        Mono<Monitor> noMonitorReturned = monitorService.findMonitorByEmail(email2);

        //ASSERT
        StepVerifier.create(returnedMonitor)
                .assertNext(monitor -> assertEquals(email1, monitor.getEmail())).verifyComplete();

        StepVerifier.create(noMonitorReturned).verifyError(ResponseStatusException.class);
    }
}
