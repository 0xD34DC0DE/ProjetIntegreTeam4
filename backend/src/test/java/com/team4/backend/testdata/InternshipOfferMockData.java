package com.team4.backend.testdata;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.model.InternshipOffer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
                .emailOfMonitor("rickJones@desjardins.com")
                .description("Développeur Web")
                .listEmailInterestedStudents(getInterestedStudentsEmailList())
                .build();
    }

    public static InternshipOfferCreationDto getInternshipOfferDto() {
        return InternshipOfferCreationDto.builder()
                .limitDateToApply(LocalDate.now().plusMonths(1))
                .beginningDate(LocalDate.now().plusMonths(2))
                .endingDate(LocalDate.now().plusMonths(6))
                .minSalary(22.5f)
                .maxSalary(23.5f)
                .companyName("desjardins")
                .emailOfMonitor("rickJones@desjardins.com")
                .description("Développeur Web")
                .build();
    }

    public static List<String> getInterestedStudentsEmailList() {
        return Arrays.asList("student1@email.com", "student2@email.com");
    }
    
}
