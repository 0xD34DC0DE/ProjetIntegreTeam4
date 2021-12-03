package com.team4.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

public class InternshipDetailsDto extends InternshipDto implements Serializable {

    @Builder(builderMethodName = "internshipDetailedDtoBuilder")
    public InternshipDetailsDto(String monitorEmail,
                                String studentEmail,
                                String internshipManagerEmail,
                                LocalDate startDate,
                                LocalDate endDate) {
        super(monitorEmail, studentEmail, internshipManagerEmail, startDate, endDate);
    }

}
