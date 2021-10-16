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
public class MonitorCreationDto extends UserCreationDto implements Serializable {

    private String companyName;

    @Builder
    public MonitorCreationDto(String id,
                              String email,
                              String password,
                              String firstName,
                              String lastName,
                              String companyName,
                              LocalDate registrationDate,
                              String phoneNumber) {
        super(id,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.MONITOR);
        this.companyName = companyName;
    }

}
