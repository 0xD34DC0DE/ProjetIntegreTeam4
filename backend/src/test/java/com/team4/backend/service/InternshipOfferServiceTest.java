package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .monitor(Monitor.monitorBuilder().email("rickJones@desjardins.com").build())
                .description("Développeur Web")
                .build();

        InternshipOfferDto internshipOfferDTO = new InternshipOfferDto(internshipOffer);

        when(monitorService.findMonitorByEmail(internshipOfferDTO.getEmailOfMonitor())).thenReturn(Mono.just(internshipOffer.getMonitor()));
        when(internshipOfferRepository.save(new InternshipOffer(internshipOfferDTO, internshipOffer.getMonitor()))).thenReturn(Mono.just(internshipOffer));

        //ACT
        Mono<InternshipOfferDto> savedInternshipOffer = internshipOfferService.addAnInternshipOffer(internshipOfferDTO);

        //ASSERT
        StepVerifier.create(savedInternshipOffer)
                .assertNext(s -> {
                    assertEquals(internshipOfferDTO.getLimitDateToApply(), s.getLimitDateToApply());
                    assertEquals(internshipOfferDTO.getBeginningDate(), s.getBeginningDate());
                    assertEquals(internshipOfferDTO.getEndingDate(), s.getEndingDate());
                    assertEquals(internshipOfferDTO.getCompanyName(), s.getCompanyName());
                    assertEquals(internshipOfferDTO.getDescription(), s.getDescription());
                    assertEquals(internshipOfferDTO.getMinSalary(), s.getMinSalary());
                    assertEquals(internshipOfferDTO.getMaxSalary(), s.getMaxSalary());
                }).verifyComplete();
    }
}
