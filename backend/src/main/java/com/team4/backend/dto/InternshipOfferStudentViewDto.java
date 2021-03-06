package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipOfferStudentViewDto extends InternshipOfferDetailsDto {

    Boolean hasAlreadyApplied;

    Boolean isExclusive;

    @Builder(builderMethodName = "internshipOfferStudentViewDtoBuilder")
    public InternshipOfferStudentViewDto(String id,
                                         String title,
                                         LocalDate limitDateToApply,
                                         LocalDate beginningDate,
                                         LocalDate endingDate,
                                         String companyName,
                                         String description,
                                         Float minSalary,
                                         Float maxSalary,
                                         Boolean hasAlreadyApplied,
                                         Boolean isExclusive) {
        super(id,
                title,
                limitDateToApply,
                beginningDate,
                endingDate,
                companyName,
                description,
                minSalary,
                maxSalary);
        this.hasAlreadyApplied = hasAlreadyApplied;
        this.isExclusive = isExclusive;
    }

}
