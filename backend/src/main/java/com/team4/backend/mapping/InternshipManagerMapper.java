package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipManagerDetailsDto;
import com.team4.backend.dto.InternshipManagerProfileDto;
import com.team4.backend.model.InternshipManager;

public abstract class InternshipManagerMapper {

    public static InternshipManagerDetailsDto toDto(InternshipManager internshipManager) {
        return InternshipManagerDetailsDto.internshipManagerDetailsDtoBuilder()
                .id(internshipManager.getId())
                .email(internshipManager.getEmail())
                .firstName(internshipManager.getFirstName())
                .lastName(internshipManager.getLastName())
                .profileImageId(internshipManager.getProfileImageId())
                .phoneNumber(internshipManager.getPhoneNumber())
                .registrationDate(internshipManager.getRegistrationDate())
                .build();
    }

    public static InternshipManager toEntity(InternshipManagerDetailsDto internshipManagerDto) {
        return InternshipManager.internshipManagerBuilder()
                .id(internshipManagerDto.getId())
                .email(internshipManagerDto.getEmail())
                .password(internshipManagerDto.getPassword())
                .firstName(internshipManagerDto.getFirstName())
                .lastName(internshipManagerDto.getLastName())
                .profileImageId(internshipManagerDto.getProfileImageId())
                .phoneNumber(internshipManagerDto.getPhoneNumber())
                .registrationDate(internshipManagerDto.getRegistrationDate())
                .build();
    }

    public static InternshipManagerProfileDto toProfileDto(InternshipManager internshipManager) {
        return InternshipManagerProfileDto.internshipManagerProfileDtoBuilder()
                .id(internshipManager.getId())
                .email(internshipManager.getEmail())
                .firstName(internshipManager.getFirstName())
                .lastName(internshipManager.getLastName())
                .registrationDate(internshipManager.getRegistrationDate())
                .phoneNumber(internshipManager.getPhoneNumber())
                .build();
    }

}
