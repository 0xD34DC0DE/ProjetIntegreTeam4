package com.team4.backend.testdata;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;

import java.time.LocalDate;

public abstract class InternshipOfferMockData {

    public static InternshipOffer getInternshipOffer() {
        return InternshipOffer.builder()
                .id("234dsd2egd54ter")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .monitor(Monitor.monitorBuilder().email("rickJones@desjardins.com").build())
                .description("Développeur Web")
                .build();
    }

    public static InternshipOfferDto getInternshipOfferDto() {
        return InternshipOfferDto.builder()
                .id("234dsd2egd54ter")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .emailOfMonitor("rickJones@desjardins.com")
                .description("Développeur Web")
                .build();

        return internshipOfferDto;
    }

    public static InternshipOfferDto getInternshipOfferDtoWithNonExistentMonitor() {
        final InternshipOfferDto internshipOfferDto =  InternshipOfferDto.builder()
                .id("das0924kdads")
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .emailOfMonitor("noneExistent@desjardins.com")
                .description("Développeur Web")
                .build();

        return internshipOfferDto;
    }
}
