package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupervisorCreationDto extends UserDetailsDto implements Serializable {

    private Set<String> studentEmails;

    @Builder
    public SupervisorCreationDto(String id,
                                 String email,
                                 String password,
                                 String firstName,
                                 String lastName,
                                 Set<String> studentEmails,
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
                Role.SUPERVISOR,
                profileImageId);
        this.studentEmails = studentEmails;
    }

}
