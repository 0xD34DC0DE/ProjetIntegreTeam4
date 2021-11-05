package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InternshipDto {

    private String monitorEmail;

    private String studentEmail;

    private String internshipManagerEmail;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder
    public InternshipDto(String monitorEmail,
                         String studentEmail,
                         String internshipManagerEmail,
                         LocalDate startDate,
                         LocalDate endDate) {
        this.monitorEmail = monitorEmail;
        this.studentEmail = studentEmail;
        this.internshipManagerEmail = internshipManagerEmail;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
