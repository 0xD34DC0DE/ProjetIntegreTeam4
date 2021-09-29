package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.testdata.MonitorMockData;
import com.team4.backend.util.PBKDF2Encoder;
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

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @Mock
    UserService userService;

    @InjectMocks
    MonitorService monitorService;

    @Test
    void findMonitorByEmailExists() {
        //ARRANGE
        String email = "marcM@desjardin.com";
        Mono<Monitor> existingMonitor = Mono.just(Monitor.monitorBuilder().email(email).build());

        when(monitorRepository.findByEmail(email)).thenReturn(existingMonitor);

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

        when(monitorRepository.findByEmail(email)).thenReturn(Mono.empty());

        //ACT
        Mono<Monitor> monitorMono = monitorService.findMonitorByEmail(email);

        //ASSERT
        StepVerifier.create(monitorMono)
                .verifyError(ResponseStatusException.class);
    }

    @Test
    void shouldCreateMonitor() {
        //ARRANGE
        Monitor monitor = MonitorMockData.getMockMonitor();
        monitor.setId(null); // Frontend gives null id

//        when(monitorRepository.save(any(Monitor.class)))
//                .thenReturn(Mono.just(monitor).map(v -> {
//                            v.setId("615259e03835be1f53bd49e4");
//                            return v;
//                        }
//                ));
//
//        when(pbkdf2Encoder.encode(any(String.class))).thenReturn("base64encryptedtoken");
        when(userService.existsByEmail(monitor.getEmail())).thenReturn(Mono.just(false));

        //ACT
        Mono<Monitor> monitorMono = monitorService.registerMonitor(monitor);

        //ASSERT
        StepVerifier.create(monitorMono).consumeNextWith(m -> {
            assertNotNull(m.getId());
            assertNotEquals(MonitorMockData.getMockMonitor().getPassword(), m.getPassword());
        });
    }

    @Test
    void shouldNotCreateMonitor() {
        //ARRANGE
        Monitor monitor = MonitorMockData.getMockMonitor();
        monitor.setId(null); // Frontend gives null id

        when(userService.existsByEmail(monitor.getEmail())).thenReturn(Mono.just(true));

        //ACT
        Mono<Monitor> monitorMono = monitorService.registerMonitor(monitor);

        //ASSERT
        StepVerifier.create(monitorMono).expectError(UserAlreadyExistsException.class).verify();
    }

}
