package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.exception.UserNotFoundException;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.testdata.InternshipOfferMockData;
import lombok.extern.java.Log;
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

@Log
@ExtendWith(MockitoExtension.class)
public class InternshipOfferServiceTest {

    @Mock
    InternshipOfferRepository internshipOfferRepository;

    @Mock
    MonitorService monitorService;

    @InjectMocks
    InternshipOfferService internshipOfferService;

    @Test
    void shouldCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(true));
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOffer> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void shouldNotCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferCreationDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(false));

        //ACT
        Mono<InternshipOffer> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .expectError(UserNotFoundException.class)
                .verify();
    }
}
