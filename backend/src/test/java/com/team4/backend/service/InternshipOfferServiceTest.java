package com.team4.backend.service;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.repository.InternshipOfferRepository;
import com.team4.backend.testdata.InternshipOfferMockData;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        InternshipOfferDto internshipOfferDTO = InternshipOfferMockData.getInternshipOfferDto();
        InternshipOffer internshipOffer = InternshipOfferMockData.getInternshipOffer();

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
