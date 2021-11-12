package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternshipContractCreationDto extends InternshipContractDto implements Serializable {

    private String address;

    private String dailySchedule;

    private Float hourlyRate;

    private Float hoursPerWeek;

    @Builder(builderMethodName = "internshipContractCreationDtoBuilder")
    public InternshipContractCreationDto(String contractId,
                                         String internshipOfferId,
                                         String studentEmail,
                                         String address,
                                         String dailySchedule,
                                         Float hourlyRate,
                                         Float hoursPerWeek) {
        super(internshipOfferId, studentEmail, contractId);
        this.address = address;
        this.dailySchedule = dailySchedule;
        this.hourlyRate = hourlyRate;
        this.hoursPerWeek = hoursPerWeek;
    }
}
