package com.team4.backend.testdata;

import com.team4.backend.dto.InternshipDetailsDto;
import com.team4.backend.model.Internship;

import java.time.LocalDate;

public abstract class InternshipMockData {

    public static Internship getInternship() {
        return Internship
                .builder()
                .id("internship-id")
                .monitorEmail("monitorTest@gmail.com")
                .internshipManagerEmail("internshipManagerTest@gmail.com")
                .studentEmail("studentTest@gmail.com")
                .beginningDate(LocalDate.now().plusDays(15))
                .endingDate(LocalDate.now().plusMonths(4))
                .build();
    }

    public static InternshipDetailsDto getInternshipDetailedDto() {
        return InternshipDetailsDto
                .internshipDetailedDtoBuilder()
                .monitorEmail("monitorTest@gmail.com")
                .internshipManagerEmail("internshipManagerTest@gmail.com")
                .studentEmail("studentTest@gmail.com")
                .startDate(LocalDate.now().plusDays(15))
                .endDate(LocalDate.now().plusMonths(4))
                .build();
    }

}
