package com.team4.backend.mapping;

import com.team4.backend.dto.SupervisorCreationDto;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class SupervisorMapper {

    public static Supervisor toEntity(SupervisorCreationDto supervisorDto) {
        return Supervisor.supervisorBuilder()
                .email(supervisorDto.getEmail())
                .password(supervisorDto.getPassword())
                .firstName(supervisorDto.getFirstName())
                .lastName(supervisorDto.getLastName())
                .studentTimestampedEntries(new HashSet<>())
                .registrationDate(LocalDate.now())
                .phoneNumber(supervisorDto.getPhoneNumber())
                .profileImageId("")
                .build();
    }

    public static SupervisorCreationDto toDetailsDto(Supervisor supervisor) {
        return SupervisorCreationDto.builder()
                .id(supervisor.getId())
                .email(supervisor.getEmail())
                .firstName(supervisor.getFirstName())
                .lastName(supervisor.getLastName())
                .studentEmails(supervisor.getStudentTimestampedEntries().stream()
                        .map(TimestampedEntry::getEmail)
                        .collect(Collectors.toSet()))
                .registrationDate(supervisor.getRegistrationDate())
                .phoneNumber(supervisor.getPhoneNumber())
                .profileImageId(supervisor.getProfileImageId())
                .build();
    }

}
