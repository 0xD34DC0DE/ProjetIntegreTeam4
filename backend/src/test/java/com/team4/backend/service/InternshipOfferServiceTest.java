package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        when(monitorService.findMonitorByEmail(internshipOfferDTO.getEmailOfMonitor()))
                .thenReturn(Mono.just(internshipOffer.getMonitor()));

        when(internshipOfferRepository.save(any(InternshipOffer.class)))
                .thenReturn(Mono.just(internshipOffer));

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

        when(monitorService.findMonitorByEmail(any(String.class)))
                .thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        //ACT
        Mono<InternshipOfferDto> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .expectError(ResponseStatusException.class)
                .verify();
    }

}
