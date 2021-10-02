package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InternshipOfferDto implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDateToApply;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginningDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

    private String companyName;

    private String description;

    private Float minSalary;

    private Float maxSalary;

    public InternshipOfferDto(LocalDate limitDateToApply,
                              LocalDate beginningDate,
                              LocalDate endingDate,
                              String companyName,
                              String description,
                              Float minSalary,
                              Float maxSalary) {
        this.limitDateToApply = limitDateToApply;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.companyName = companyName;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }
}
