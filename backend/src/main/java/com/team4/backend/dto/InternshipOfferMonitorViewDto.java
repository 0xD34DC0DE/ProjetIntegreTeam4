package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipOfferMonitorViewDto extends InternshipOfferDto implements Serializable {

    String id;

    private List<String> listEmailInterestedStudents;
    @Builder
    public InternshipOfferMonitorViewDto(LocalDate limitDateToApply,
                                         LocalDate beginningDate,
                                         LocalDate endingDate,
                                         String companyName,
                                         String description,
                                         Float minSalary,
                                         Float maxSalary,
                                         String id,
                                         List<String> listEmailInterestedStudents) {
        super(limitDateToApply,
                beginningDate,
                endingDate,
                companyName,
                description,
                minSalary,
                maxSalary);
        this.id = id;
        this.listEmailInterestedStudents = listEmailInterestedStudents;
    }
}
