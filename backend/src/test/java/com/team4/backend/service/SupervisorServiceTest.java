package com.team4.backend.service;

import com.team4.backend.exception.UserAlreadyExistsException;
import com.team4.backend.model.Supervisor;
import com.team4.backend.repository.SupervisorRepository;
import com.team4.backend.testdata.SupervisorMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupervisorServiceTest {

    @Mock
    SupervisorRepository supervisorRepository;

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @Mock
    UserService userService;

    @InjectMocks
    SupervisorService supervisorService;

    @Test
    void shouldCreateSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        supervisor.setId(null); // Frontend gives null id

        when(supervisorRepository.save(any(Supervisor.class)))
                .thenReturn(Mono.just(supervisor).map(s -> {
                            s.setId("615a32ce577ae63d7b159b17");
                            return s;
                        }
                ));

        when(pbkdf2Encoder.encode(any(String.class))).thenReturn("base64encryptedtoken");
        when(userService.existsByEmail(supervisor.getEmail())).thenReturn(Mono.just(false));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.registerSupervisor(supervisor);

        //ASSERT
        StepVerifier.create(supervisorMono).consumeNextWith(s -> {
            assertNotNull(s.getId());
            assertNotEquals(SupervisorMockData.getMockSupervisor().getPassword(), s.getPassword());
        }).verifyComplete();
    }

    @Test
    void shouldNotCreateSupervisor() {
        //ARRANGE
        Supervisor supervisor = SupervisorMockData.getMockSupervisor();
        supervisor.setId(null); // Frontend gives null id

        when(userService.existsByEmail(supervisor.getEmail())).thenReturn(Mono.just(true));

        //ACT
        Mono<Supervisor> supervisorMono = supervisorService.registerSupervisor(supervisor);

        //ASSERT
        StepVerifier.create(supervisorMono).expectError(UserAlreadyExistsException.class).verify();
    }

}
