package com.team4.backend.mapping;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.model.Supervisor;

public abstract class SupervisorMapper {

    public static Supervisor toEntity(SupervisorDetailsDto supervisorDto) {
        return Supervisor.supervisorBuilder()
                .email(supervisorDto.getEmail())
                .password(supervisorDto.getPassword())
                .firstName(supervisorDto.getFirstName())
                .lastName(supervisorDto.getLastName())
                .studentEmails(supervisorDto.getStudentEmails())
                .registrationDate(supervisorDto.getRegistrationDate())
                .phoneNumber(supervisorDto.getPhoneNumber())
                .build();
    }

    public static SupervisorDetailsDto toDto(Supervisor supervisor) {
        return SupervisorDetailsDto.builder()
                .id(supervisor.getId())
                .email(supervisor.getEmail())
                .firstName(supervisor.getFirstName())
                .lastName(supervisor.getLastName())
                .studentEmails(supervisor.getStudentEmails())
                .registrationDate(supervisor.getRegistrationDate())
                .phoneNumber(supervisor.getPhoneNumber())
                .build();
    }

}
