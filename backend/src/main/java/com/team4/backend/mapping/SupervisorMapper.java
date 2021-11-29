package com.team4.backend.mapping;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.dto.SupervisorProfileDto;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class SupervisorMapper {

    public static Supervisor toEntity(SupervisorDetailsDto supervisorDto) {
        return Supervisor.supervisorBuilder()
                .email(supervisorDto.getEmail())
                .password(supervisorDto.getPassword())
                .firstName(supervisorDto.getFirstName())
                .lastName(supervisorDto.getLastName())
                .studentTimestampedEntries(new HashSet<>())
                .registrationDate(LocalDate.now())
                .phoneNumber(supervisorDto.getPhoneNumber())
                .build();
    }

    public static SupervisorDetailsDto toDetailsDto(Supervisor supervisor) {
        return SupervisorDetailsDto.builder()
                .id(supervisor.getId())
                .email(supervisor.getEmail())
                .firstName(supervisor.getFirstName())
                .lastName(supervisor.getLastName())
                .studentEmails(supervisor.getStudentTimestampedEntries().stream()
                        .map(TimestampedEntry::getEmail)
                        .collect(Collectors.toSet()))
                .registrationDate(supervisor.getRegistrationDate())
                .phoneNumber(supervisor.getPhoneNumber())
                .build();
    }

    public static SupervisorProfileDto toProfileDto(Supervisor supervisor){
        return SupervisorProfileDto.builder()
                .id(supervisor.getId())
                .email(supervisor.getEmail())
                .firstName(supervisor.getFirstName())
                .lastName(supervisor.getLastName())
                .registrationDate(supervisor.getRegistrationDate())
                .phoneNumber(supervisor.getPhoneNumber())
                .nbrOfAssignedStudents(supervisor.getStudentTimestampedEntries().size())
                .build();
    }

}
