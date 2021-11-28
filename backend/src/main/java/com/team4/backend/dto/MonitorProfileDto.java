package com.team4.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MonitorProfileDto extends UserProfileDto implements Serializable {

    private String companyName;

    public MonitorProfileDto(String id,
                             String email,
                             String firstName,
                             String lastName,
                             LocalDate registrationDate,
                             String phoneNumber,
                             String companyName) {
        super(id, email, firstName, lastName, registrationDate, phoneNumber);
        this.companyName = companyName;
    }
    
}
