package com.team4.backend.testdata;

import com.team4.backend.model.Internship;
import com.team4.backend.model.InternshipOffer;

import java.time.LocalDate;

public abstract class InternshipMockData {

    public static Internship getInternship() {
        return Internship
                .builder()
                .id("internship-id")
                .monitorEmail("monitorTest@gmail.com")
                .internshipManagerEmail("internshipManagerTest@gmail.com")
                .studentEmail("studentTest@gmail.com")
                .startDate(LocalDate.now().plusDays(15))
                .endDate(LocalDate.now().plusMonths(4))
                .build();
    }
}
