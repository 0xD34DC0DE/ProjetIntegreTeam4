package com.team4.backend.mapping;

import com.team4.backend.dto.MonitorDetailsDto;
import com.team4.backend.dto.MonitorProfileDto;
import com.team4.backend.dto.SupervisorProfileDto;
import com.team4.backend.model.Monitor;

public abstract class MonitorMapper {

    public static Monitor toEntity(MonitorDetailsDto monitorDto) {
        return Monitor.monitorBuilder()
                .email(monitorDto.getEmail())
                .password(monitorDto.getPassword())
                .firstName(monitorDto.getFirstName())
                .lastName(monitorDto.getLastName())
                .companyName(monitorDto.getCompanyName())
                .registrationDate(monitorDto.getRegistrationDate())
                .phoneNumber(monitorDto.getPhoneNumber())
                .build();
    }

    public static MonitorDetailsDto toDto(Monitor monitor) {
        return MonitorDetailsDto.builder()
                .id(monitor.getId())
                .email(monitor.getEmail())
                .firstName(monitor.getFirstName())
                .lastName(monitor.getLastName())
                .companyName(monitor.getCompanyName())
                .registrationDate(monitor.getRegistrationDate())
                .phoneNumber(monitor.getPhoneNumber())
                .build();
    }

    public static MonitorProfileDto toProfileDto(Monitor monitor) {
        return MonitorProfileDto.builder()
                .id(monitor.getId())
                .email(monitor.getEmail())
                .firstName(monitor.getFirstName())
                .lastName(monitor.getLastName())
                .registrationDate(monitor.getRegistrationDate())
                .phoneNumber(monitor.getPhoneNumber())
                .companyName(monitor.getCompanyName())
                .build();
    }

}