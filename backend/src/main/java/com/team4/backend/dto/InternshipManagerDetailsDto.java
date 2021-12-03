package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipManagerDetailsDto extends UserDetailsDto implements Serializable {

    @Builder(builderMethodName = "internshipManagerDetailsDtoBuilder")
    public InternshipManagerDetailsDto(String id,
                                       String email,
                                       String password,
                                       String firstName,
                                       String lastName,
                                       LocalDate registrationDate,
                                       String phoneNumber,
                                       String profileImageId) {
        super(id,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.INTERNSHIP_MANAGER,
                profileImageId);
    }

}
