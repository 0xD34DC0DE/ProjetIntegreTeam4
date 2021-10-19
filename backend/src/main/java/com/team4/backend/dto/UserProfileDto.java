package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserProfileDto extends UserDto implements Serializable {

    protected String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate registrationDate;

    public UserProfileDto(String id,
                           String email,
                           String firstName,
                           String lastName,
                           LocalDate registrationDate,
                           String phoneNumber) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
    }
}
