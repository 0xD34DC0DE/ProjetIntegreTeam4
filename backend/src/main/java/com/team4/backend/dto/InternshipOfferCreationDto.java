package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipOfferCreationDto extends InternshipOfferBaseDto implements Serializable {

    private String emailOfMonitor;

    @Builder
    public InternshipOfferCreationDto(LocalDate limitDateToApply,
                                      LocalDate beginningDate,
                                      LocalDate endingDate,
                                      String companyName,
                                      String description,
                                      Float minSalary,
                                      Float maxSalary,
                                      String emailOfMonitor) {
        super(limitDateToApply,
                beginningDate,
                endingDate,
                companyName,
                description,
                minSalary,
                maxSalary);
        this.emailOfMonitor = emailOfMonitor;
    }
}


