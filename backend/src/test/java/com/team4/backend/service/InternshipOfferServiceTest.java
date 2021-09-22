package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDTO;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.repository.InternshipOfferRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
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
    void addAnInternshipOffer() {
        //ARRANGE
        InternshipOffer internshipOffer = InternshipOffer.builder()
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .companyName("desjardins")
                .monitor(Monitor.monitorBuilder().email("rickJones@desjardins.com").build())
                .description("Développeur Web")
                .build();
        InternshipOfferDTO internshipOfferDTO = new InternshipOfferDTO(internshipOffer);

        when(monitorService.findMonitorByEmail(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(internshipOffer.getMonitor()));
        when(internshipOfferRepository.save(new InternshipOffer(internshipOfferDTO,internshipOffer.getMonitor()))).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOfferDTO> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .assertNext(s -> {
                    assertEquals(internshipOfferDTO.getLimitDateToApply(), s.getLimitDateToApply());
                    assertEquals(internshipOfferDTO.getBeginningDate(), s.getBeginningDate());
                    assertEquals(internshipOfferDTO.getEndingDate(), s.getEndingDate());
                    assertEquals(internshipOfferDTO.getCompanyName(), s.getCompanyName());
                    assertEquals(internshipOfferDTO.getDescription(), s.getDescription());
                }).verifyComplete();
    }
}
