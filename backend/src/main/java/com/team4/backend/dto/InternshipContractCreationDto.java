package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InternshipContractCreationDto implements Serializable {

    private String internshipOfferId;

    private String studentEmail;

    private String address;

    private String dailySchedule;

    private Float hourlyRate;

    private Float hoursPerWeek;

    @Builder
    public InternshipContractCreationDto(String internshipOfferId, String studentEmail, String address, String dailySchedule, Float hourlyRate, Float hoursPerWeek) {
        this.internshipOfferId = internshipOfferId;
        this.studentEmail = studentEmail;
        this.address = address;
        this.dailySchedule = dailySchedule;
        this.hourlyRate = hourlyRate;
        this.hoursPerWeek = hoursPerWeek;
    }

}
