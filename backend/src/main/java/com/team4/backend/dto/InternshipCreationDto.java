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
public class InternshipCreationDto extends InternshipDto implements Serializable {

    private String internshipOfferId;

    @Builder(builderMethodName = "internshipCreationDtoBuilder")
    public InternshipCreationDto(String monitorEmail,
                                 String studentEmail,
                                 String internshipManagerEmail,
                                 LocalDate startDate,
                                 LocalDate endDate) {
        super(monitorEmail, studentEmail, internshipManagerEmail, startDate, endDate);
    }
}

