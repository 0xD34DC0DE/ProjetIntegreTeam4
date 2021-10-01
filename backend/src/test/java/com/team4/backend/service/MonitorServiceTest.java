package com.team4.backend.service;

import com.team4.backend.exception.DoNotExistException;
import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Monitor;
import com.team4.backend.repository.MonitorRepository;
import com.team4.backend.testdata.MonitorMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Assertions;
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
    void shouldExistByEmailAndIsEnabledTrue() {
        //ARRANGE
        String email = "marcM@desjardin.com";

        when(monitorRepository.existsByEmailAndIsEnabledTrue(any(String.class))).thenReturn(Mono.just(true));

        //ACT
        Mono<Boolean> monitorExist = monitorService.existsByEmailAndIsEnabledTrue(email);

        //ASSERT
        StepVerifier.create(monitorExist)
                .assertNext(Assertions::assertTrue)
                .verifyComplete();
    }

    @Test
    void shouldNotExistByEmailAndIsEnabledTrue() {
        //ARRANGE
        String email = "inexistantEmail@gmail.com";

        when(monitorRepository.existsByEmailAndIsEnabledTrue(any(String.class))).thenReturn(Mono.just(false));

        //ACT
        Mono<Boolean> monitorDoNotExist = monitorService.existsByEmailAndIsEnabledTrue(email);

        //ASSERT
        StepVerifier.create(monitorDoNotExist)
                .verifyError(DoNotExistException.class);
    }

    @Test
    void shouldCreateMonitor() {
        //ARRANGE
        Monitor monitor = MonitorMockData.getMockMonitor();
        monitor.setId(null); // Frontend gives null id

        when(monitorRepository.save(any(Monitor.class)))
                .thenReturn(Mono.just(monitor).map(m -> {
                            m.setId("615259e03835be1f53bd49e4");
                            return m;
                        }
                ));

        when(pbkdf2Encoder.encode(any(String.class))).thenReturn("base64encryptedtoken");
        when(userService.existsByEmail(monitor.getEmail())).thenReturn(Mono.just(false));

        //ACT
        Mono<Monitor> monitorMono = monitorService.registerMonitor(monitor);

        //ASSERT
        StepVerifier.create(monitorMono).consumeNextWith(m -> {
            assertNotNull(m.getId());
            assertNotEquals(MonitorMockData.getMockMonitor().getPassword(), m.getPassword());
        }).verifyComplete();
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
