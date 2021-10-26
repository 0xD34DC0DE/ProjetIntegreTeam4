package com.team4.backend.service;

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

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
        InternshipManager internshipManager = InternshipManagerMockData.GetInternshipManager();
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
            assertNotEquals(InternshipManagerMockData.GetInternshipManager().getPassword(), result.getPassword());
        }).verifyComplete();
    }

}
