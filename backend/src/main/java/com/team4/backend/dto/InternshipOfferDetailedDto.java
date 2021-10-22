package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InternshipOfferDetailedDto extends InternshipOfferDto implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDateToApply;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginningDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

    private Float minSalary;

    private Float maxSalary;

    @Builder(builderMethodName = "internshipOfferDetailedDtoBuilder")
    public InternshipOfferDetailedDto(String id,
                                      LocalDate limitDateToApply,
                                      LocalDate beginningDate,
                                      LocalDate endingDate,
                                      String companyName,
                                      String description,
                                      Float minSalary,
                                      Float maxSalary) {
        super(id,
                companyName,
                description
        );

        this.limitDateToApply = limitDateToApply;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

}