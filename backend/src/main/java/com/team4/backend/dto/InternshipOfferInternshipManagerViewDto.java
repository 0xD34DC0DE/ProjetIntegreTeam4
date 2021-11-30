package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InternshipOfferInternshipManagerViewDto extends InternshipOfferDetailsDto implements Serializable {

    private Boolean isExclusive;

    @Builder(builderMethodName = "internshipOfferInternshipManagerViewDtoBuilder")
    public InternshipOfferInternshipManagerViewDto(String id,
                                                   String title,
                                                   LocalDate limitDateToApply,
                                                   LocalDate beginningDate,
                                                   LocalDate endingDate,
                                                   String companyName,
                                                   String description,
                                                   Float minSalary,
                                                   Float maxSalary,
                                                   Boolean isExclusive) {
        super(id, title, limitDateToApply, beginningDate, endingDate, companyName, description, minSalary, maxSalary);
        this.isExclusive = isExclusive;
    }
}
