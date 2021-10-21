package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
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
public class SupervisorDetailsDto extends UserDetailsDto implements Serializable {

    private List<String> studentEmails;

    @Builder
    public SupervisorDetailsDto(String id,
                                String email,
                                String password,
                                String firstName,
                                String lastName,
                                List<String> studentEmails,
                                LocalDate registrationDate,
                                String phoneNumber) {
        super(id,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.SUPERVISOR);
        this.studentEmails = studentEmails;
    }

}