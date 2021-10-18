package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserCreationDto extends UserDto implements Serializable {

    protected String password;

    protected String phoneNumber;

    protected LocalDate registrationDate;

    protected Role role;

    public UserCreationDto(String id,
                           String email,
                           String password,
                           String firstName,
                           String lastName,
                           LocalDate registrationDate,
                           String phoneNumber,
                           Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = Optional.ofNullable(registrationDate).orElse(LocalDate.now());
        this.role = role;
    }

}

