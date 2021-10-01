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
public class MonitorDto extends UserDto implements Serializable {

    @Builder
    public MonitorDto(String id,
                      String registrationNumber,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      LocalDate registrationDate,
                      String phoneNumber) {
        super(id,
                registrationNumber,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.MONITOR);
    }

}
