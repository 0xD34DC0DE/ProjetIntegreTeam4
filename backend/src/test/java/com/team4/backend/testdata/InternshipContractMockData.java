package com.team4.backend.testdata;

import com.team4.backend.model.InternshipContract;
import com.team4.backend.model.Signature;

import java.time.LocalDate;

public abstract class InternshipContractMockData {

    public static InternshipContract getInternshipContract() {
        return InternshipContract.builder()
                .id("kfo39fk3lda2dsadad")
                .internshipOfferId("dsaida932kklda32")
                .address("323 rue Lapierre, Montréal, Québec")
                .beginningDate(LocalDate.now().minusMonths(2))
                .endingDate(LocalDate.now().plusWeeks(2))
                .dailySchedule("2:00 à 10:00")
                .hoursPerWeek(35.0f)
                .hourlyRate(25.0f)
                .internTasks("Work")
                .studentSignature(Signature.builder()
                        .hasSigned(true)
                        .signDate(LocalDate.now().plusDays(4))
                        .userId("fdasd3rfasda3edra")
                        .build())
                .internshipManagerSignature(Signature.builder()
                        .hasSigned(true)
                        .signDate(LocalDate.now().minusWeeks(1))
                        .userId("asdasd3eda3dwdad")
                        .build())
                .monitorSignature(Signature.builder()
                        .hasSigned(true)
                        .signDate(LocalDate.now().plusDays(4))
                        .userId("gdsf34sfdfs32rf3")
                        .build())
                .build();
    }

}
