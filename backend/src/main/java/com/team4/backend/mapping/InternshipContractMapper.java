package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.model.InternshipContract;
import com.team4.backend.model.Signature;

public abstract class InternshipContractMapper {

    public static InternshipContract toEntity(InternshipContractDto internshipContractDto) {
        return InternshipContract.builder()
                .id(internshipContractDto.getContractId())
                .internshipOfferId(internshipContractDto.getInternshipOfferId())
                .internshipManagerSignature(new Signature())
                .studentSignature(new Signature())
                .monitorSignature(new Signature())
                .build();
    }

    public static InternshipContractDto toDto(InternshipContract internshipContract) {
        return InternshipContractDto.builder()
                .contractId(internshipContract.getId())
                .internshipOfferId(internshipContract.getInternshipOfferId())
                .build();
    }

    public static InternshipContract toEntity(InternshipContractCreationDto internshipContractCreationDto) {
        return InternshipContract.builder()
                .internshipOfferId(internshipContractCreationDto.getInternshipOfferId())
                .address(internshipContractCreationDto.getAddress())
                .hoursPerWeek(internshipContractCreationDto.getHoursPerWeek())
                .hourlyRate(internshipContractCreationDto.getHourlyRate())
                .dailySchedule(internshipContractCreationDto.getDailySchedule())
                .monitorSignature(new Signature())
                .studentSignature(new Signature())
                .internshipManagerSignature(new Signature())
                .build();
    }
}
