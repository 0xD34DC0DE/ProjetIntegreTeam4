package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class InternshipOfferDto implements Serializable {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDateToApply;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginningDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

    private String companyName;

    private String emailOfMonitor;

    private String description;

    private Float minSalary, maxSalary;

    private boolean isValidated;

    private List<String> listEmailInterestedStudents;

    @Builder
    public InternshipOfferDto(String id,
                              LocalDate limitDateToApply,
                              LocalDate beginningDate,
                              LocalDate endingDate,
                              String emailOfMonitor,
                              String companyName,
                              String description,
                              Float minSalary,
                              Float maxSalary,
                              boolean isValidated,
                              List<String> listEmailInterestedStudents) {
        this.id = id;
        this.limitDateToApply = limitDateToApply;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.emailOfMonitor = emailOfMonitor;
        this.companyName = companyName;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.isValidated = isValidated;
        this.listEmailInterestedStudents = listEmailInterestedStudents;
    }

}
