package com.team4.backend.service;

import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.InternshipManager;
import com.team4.backend.repository.InternshipManagerRepository;
import com.team4.backend.testdata.InternshipManagerMockData;
import com.team4.backend.util.PBKDF2Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InternshipManagerServiceTest {

    @Mock
    private InternshipManagerRepository internshipManagerRepository;

    @Mock
    PBKDF2Encoder pbkdf2Encoder;

    @InjectMocks
    private InternshipManagerService internshipManagerService;

    @Test
    void shouldCreateInternshipManager() {
        //ARRANGE
        InternshipManager internshipManager = InternshipManagerMockData.getInternshipManager();
        internshipManager.setId(null); // Id is null when coming from auto-register

        when(internshipManagerRepository.save(any(InternshipManager.class)))
                .thenReturn(Mono.just(internshipManager).map(value -> {
                    value.setId("not_null_id");
                    return value;
                }));

        when(pbkdf2Encoder.encode(any(String.class))).thenReturn("encrypted");

        //ACT
        Mono<InternshipManager> internshipManagerMono = internshipManagerService.register(internshipManager);

        //ASSERT
        StepVerifier.create(internshipManagerMono).consumeNextWith(result -> {
            assertNotNull(result.getId());
            assertNotEquals(InternshipManagerMockData.getInternshipManager().getPassword(), result.getPassword());
        }).verifyComplete();
    }

    @Test
    void shouldFindByEmail() {
        //ARRANGE
        InternshipManager internshipManager = InternshipManagerMockData.getInternshipManager();

        when(internshipManagerRepository.findByEmail(anyString())).thenReturn(Mono.just(internshipManager));

        //ACT
        Mono<InternshipManager> internshipManagerMono = internshipManagerService.findByEmail(internshipManager.getEmail());

        //ASSERT
        StepVerifier.create(internshipManagerMono)
                .assertNext(i -> assertEquals(internshipManager.getEmail(), i.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindByEmail() {
        //ARRANGE
        when(internshipManagerRepository.findByEmail(anyString())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<InternshipManager> internshipManagerMono = internshipManagerService.findByEmail(anyString());

        //ASSERT
        StepVerifier.create(internshipManagerMono)
                .verifyError(UserNotFoundException.class);
    }

    @Test
    void shouldFindById() {
        //ARRANGE
        InternshipManager internshipManager = InternshipManagerMockData.getInternshipManager();

        when(internshipManagerRepository.findById(anyString())).thenReturn(Mono.just(internshipManager));

        //ACT
        Mono<InternshipManager> internshipManagerMono = internshipManagerService.findById(internshipManager.getId());

        //ASSERT
        StepVerifier.create(internshipManagerMono)
                .assertNext(i -> assertEquals(internshipManager.getEmail(), i.getEmail()))
                .verifyComplete();
    }

    @Test
    void shouldNotFindById() {
        //ARRANGE
        when(internshipManagerRepository.findById(anyString())).thenReturn(Mono.error(UserNotFoundException::new));

        //ACT
        Mono<InternshipManager> internshipManagerMono = internshipManagerService.findById(anyString());

        //ASSERT
        StepVerifier.create(internshipManagerMono)
                .verifyError(UserNotFoundException.class);
    }

}
