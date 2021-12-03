package com.team4.backend.dto;

import lombok.Builder;
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

    @Builder(builderMethodName = "monitorProfileDtoBuilder")
    public MonitorProfileDto(String id,
                             String email,
                             String firstName,
                             String lastName,
                             LocalDate registrationDate,
                             String phoneNumber,
                             String companyName,
                             String profileImageId) {
        super(id, email, firstName, lastName, registrationDate, phoneNumber, profileImageId);
        this.companyName = companyName;
    }

}
