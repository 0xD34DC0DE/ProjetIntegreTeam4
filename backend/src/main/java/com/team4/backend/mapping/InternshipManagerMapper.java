package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipManagerDto;
import com.team4.backend.model.InternshipManager;

public abstract class InternshipManagerMapper {

    public static InternshipManagerDto toDto(InternshipManager internshipManager) {
        return InternshipManagerDto.builder()
                .id(internshipManager.getId())
                .email(internshipManager.getEmail())
                .firstName(internshipManager.getFirstName())
                .lastName(internshipManager.getLastName())
                .phoneNumber(internshipManager.getPhoneNumber())
                .registrationDate(internshipManager.getRegistrationDate())
                .registrationNumber(internshipManager.getRegistrationNumber())
                .build();
    }

    public static InternshipManager toEntity(InternshipManagerDto internshipManagerDto) {
        return InternshipManager.internshipManagerBuilder()
                .id(internshipManagerDto.getId())
                .email(internshipManagerDto.getEmail())
                .password(internshipManagerDto.getPassword())
                .firstName(internshipManagerDto.getFirstName())
                .lastName(internshipManagerDto.getLastName())
                .phoneNumber(internshipManagerDto.getPhoneNumber())
                .registrationDate(internshipManagerDto.getRegistrationDate())
                .registrationNumber(internshipManagerDto.getRegistrationNumber())
                .build();
    }

}
