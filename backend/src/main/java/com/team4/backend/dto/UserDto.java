package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
public class UserDto {

    protected String id;

    protected String registrationNumber;

    protected String email;

    protected String password;

    protected String firstName;

    protected String lastName;

    protected String phoneNumber;

    protected LocalDate registrationDate;

    protected Role role;

    public UserDto(String id,
                   String registrationNumber,
                   String email,
                   String password,
                   String firstName,
                   String lastName,
                   LocalDate registrationDate,
                   String phoneNumber,
                   Role role) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = Optional.ofNullable(registrationDate).orElse(LocalDate.now());
        this.role = role;
    }
}
