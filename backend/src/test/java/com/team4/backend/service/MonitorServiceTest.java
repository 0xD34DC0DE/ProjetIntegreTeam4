package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.exception.UserNotFoundException;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
                .assertNext(Assertions::assertFalse)
                .verifyComplete();
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
        StepVerifier.create(monitorMono).assertNext(m -> {
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

    @Test
    void shouldFindByEmail() {
        //ARRANGE
        Monitor monitor = MonitorMockData.getMockMonitor();

        when(monitorRepository.findByEmail(anyString())).thenReturn(Mono.just(monitor));

        //ACT
        Mono<Monitor> monitorMono = monitorService.findByEmail(monitor.getEmail());

        //ASSERT
        StepVerifier.create(monitorMono)
                .assertNext(m -> assertEquals(monitor.getEmail(), m.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindByEmail() {
        //ARRANGE
        when(monitorRepository.findByEmail(anyString())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<Monitor> monitorMono = monitorService.findByEmail(anyString());

        //ASSERT
        StepVerifier.create(monitorMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldFindById() {
        //ARRANGE
        Monitor monitor = MonitorMockData.getMockMonitor();

        when(monitorRepository.findById(anyString())).thenReturn(Mono.just(monitor));

        //ACT
        Mono<Monitor> monitorMono = monitorService.findById(monitor.getId());

        //ASSERT
        StepVerifier.create(monitorMono)
                .assertNext(m -> assertEquals(monitor.getId(), m.getId()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindById() {
        //ARRANGE
        when(monitorRepository.findById(anyString())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<Monitor> monitorMono = monitorService.findById(anyString());

        //ASSERT
        StepVerifier.create(monitorMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldFindBAllByIds() {
        //ARRANGE
        when(monitorRepository.findAllByIds(anyList())).thenReturn(Flux.just(MonitorMockData.getMockMonitor()));

        //ACT
        Flux<Monitor> monitorFlux = monitorService.findAllByIds(anyList());

        //ASSERT
        StepVerifier.create(monitorFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

}
