package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    private String emailOfMonitor;

    private String companyName;

    private String description;

    private Float minSalary, maxSalary;

    private boolean isValidated;

    private List<String> listEmailInterestedStudents;

    private List<String> listEmailExclusiveStudents;

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
                              List<String> listEmailInterestedStudents,
                              List<String> listEmailExclusiveStudents) {
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
        this.listEmailExclusiveStudents = listEmailExclusiveStudents;
    }

}
