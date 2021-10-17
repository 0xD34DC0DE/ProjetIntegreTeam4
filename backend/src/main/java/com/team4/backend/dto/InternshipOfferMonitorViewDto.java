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
public class InternshipOfferMonitorViewDto extends InternshipOfferDetailedDto implements Serializable {

    private List<String> listEmailInterestedStudents;

    @Builder(builderMethodName = "internshipOfferMonitorViewDtoBuilder")
    public InternshipOfferMonitorViewDto(String id,
                                         LocalDate limitDateToApply,
                                         LocalDate beginningDate,
                                         LocalDate endingDate,
                                         String companyName,
                                         String description,
                                         Float minSalary,
                                         Float maxSalary,
                                         List<String> listEmailInterestedStudents) {
        super(id,
                limitDateToApply,
                beginningDate,
                endingDate,
                companyName,
                description,
                minSalary,
                maxSalary);
        this.listEmailInterestedStudents = listEmailInterestedStudents;
    }

}
