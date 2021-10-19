package com.team4.backend.dto;

import com.team4.backend.model.enums.StudentState;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentProfileDto extends UserProfileDto implements Serializable {

    private StudentState studentState;

    private Integer nbrOfExclusiveOffers;

    private Integer nbrOfAppliedOffers;

    @Builder
    public StudentProfileDto(String id,
                             String email,
                             String firstName,
                             String lastName,
                             LocalDate registrationDate,
                             String phoneNumber,
                             StudentState studentState,
                             Integer nbrOfExclusiveOffers,
                             Integer nbrOfAppliedOffers) {
        super(id, email, firstName, lastName, registrationDate, phoneNumber);
        this.studentState = studentState;
        this.nbrOfExclusiveOffers = nbrOfExclusiveOffers;
        this.nbrOfAppliedOffers = nbrOfAppliedOffers;
    }
}
