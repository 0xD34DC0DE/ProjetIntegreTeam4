package com.team4.backend.service;

import com.team4.backend.exception.DoNotExistException;
import com.team4.backend.repository.MonitorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonitorServiceTest {

    @Mock
    MonitorRepository monitorRepository;

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
}
