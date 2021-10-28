package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team4.backend.model.enums.StudentState;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentProfileDto extends UserProfileDto implements Serializable {

    private StudentState studentState;

    private Integer nbrOfExclusiveOffers;

    private Integer nbrOfAppliedOffers;

    private Integer nbrOfInterviews;

    private Boolean hasValidCv;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recentInterviewDate;

    @Builder
    public StudentProfileDto(String id,
                             String email,
                             String firstName,
                             String lastName,
                             LocalDate registrationDate,
                             String phoneNumber,
                             StudentState studentState,
                             Integer nbrOfExclusiveOffers,
                             Integer nbrOfAppliedOffers,
                             Integer nbrOfInterviews,
                             Boolean hasValidCv,
                             LocalDate recentInterviewDate) {
        super(id, email, firstName, lastName, registrationDate, phoneNumber);
        this.studentState = studentState;
        this.nbrOfExclusiveOffers = nbrOfExclusiveOffers;
        this.nbrOfAppliedOffers = nbrOfAppliedOffers;
        this.nbrOfInterviews = nbrOfInterviews;
        this.hasValidCv = hasValidCv;
        this.recentInterviewDate = recentInterviewDate;
    }
}
