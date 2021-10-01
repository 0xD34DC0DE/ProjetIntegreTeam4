package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.exception.UserDoNotExistException;
import com.team4.backend.exception.WrongCredentialsException;
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
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

        internshipOfferDTO.setId(null);

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(true));
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOfferDto> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void shouldNotCreateInternshipOffer() {
        //ARRANGE
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();

        when(monitorService.existsByEmailAndIsEnabledTrue(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(false));

        //ACT
        Mono<InternshipOfferDto> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .expectError(UserDoNotExistException.class)
                .verify();
    }
}
