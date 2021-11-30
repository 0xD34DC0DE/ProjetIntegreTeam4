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
public class SupervisorProfileDto extends UserProfileDto implements Serializable {

    private Integer nbrOfAssignedStudents;

    @Builder
    public SupervisorProfileDto(String id,
                                String email,
                                String firstName,
                                String lastName,
                                LocalDate registrationDate,
                                String phoneNumber,
                                Integer nbrOfAssignedStudents,
                                String profileImageId) {
        super(id, email, firstName, lastName, registrationDate, phoneNumber, profileImageId);
        this.nbrOfAssignedStudents = nbrOfAssignedStudents;
    }

}
