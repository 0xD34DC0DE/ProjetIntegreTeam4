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
public class InternshipOfferCreationDto extends InternshipOfferDetailsDto implements Serializable {

    private String monitorEmail;

    @Builder(builderMethodName = "internshipOfferCreationDtoBuilder")
    public InternshipOfferCreationDto(String id,
                                      String title,
                                      LocalDate limitDateToApply,
                                      LocalDate beginningDate,
                                      LocalDate endingDate,
                                      String companyName,
                                      String description,
                                      Float minSalary,
                                      Float maxSalary,
                                      String monitorEmail) {
        super(id,
                title,
                limitDateToApply,
                beginningDate,
                endingDate,
                companyName,
                description,
                minSalary,
                maxSalary);
        this.monitorEmail = monitorEmail;
    }

}


