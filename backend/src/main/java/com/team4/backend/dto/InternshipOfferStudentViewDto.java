package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipOfferStudentViewDto extends InternshipOfferBaseDto {

    String id;

    Boolean hasAlreadyApplied;

    Boolean isExclusive;

    @Builder
    public InternshipOfferStudentViewDto(LocalDate limitDateToApply,
                                         LocalDate beginningDate,
                                         LocalDate endingDate,
                                         String companyName,
                                         String description,
                                         Float minSalary,
                                         Float maxSalary,
                                         String id,
                                         Boolean hasAlreadyApplied,
                                         Boolean isExclusive) {
        super(limitDateToApply,
                beginningDate,
                endingDate,
                companyName,
                description,
                minSalary,
                maxSalary);
        this.id = id;
        this.hasAlreadyApplied = hasAlreadyApplied;
        this.isExclusive = isExclusive;
    }
}
