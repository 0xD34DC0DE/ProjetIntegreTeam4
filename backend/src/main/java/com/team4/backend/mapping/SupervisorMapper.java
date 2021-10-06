package com.team4.backend.mapping;

import com.team4.backend.dto.SupervisorDto;
import com.team4.backend.model.Supervisor;

public abstract class SupervisorMapper {

    public static Supervisor toEntity(SupervisorDto supervisorDto) {
        return Supervisor.supervisorBuilder()
                .email(supervisorDto.getEmail())
                .password(supervisorDto.getPassword())
                .firstName(supervisorDto.getFirstName())
                .lastName(supervisorDto.getLastName())
                .students(supervisorDto.getStudents())
                .registrationDate(supervisorDto.getRegistrationDate())
                .phoneNumber(supervisorDto.getPhoneNumber())
                .build();
    }

    public static SupervisorDto toDto(Supervisor supervisor) {
        return SupervisorDto.builder()
                .id(supervisor.getId())
                .email(supervisor.getEmail())
                .firstName(supervisor.getFirstName())
                .lastName(supervisor.getLastName())
                .students(supervisor.getStudents())
                .registrationDate(supervisor.getRegistrationDate())
                .phoneNumber(supervisor.getPhoneNumber())
                .build();
    }

}
