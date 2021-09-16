package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {

    private String id;

    private String registrationNumber;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate registrationDate;

    private Role role;

    public UserDto(String id,
                   String registrationNumber,
                   String email,
                   String password,
                   String firstName,
                   String lastName,
                   LocalDate registrationDate,
                   Role role) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.role = role;
    }
}
