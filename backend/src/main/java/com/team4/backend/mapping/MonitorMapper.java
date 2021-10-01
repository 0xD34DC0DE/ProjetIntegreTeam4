package com.team4.backend.mapping;

import com.team4.backend.dto.MonitorDto;
import com.team4.backend.model.Monitor;

public abstract class MonitorMapper {

    public static Monitor toEntity(MonitorDto monitorDto) {
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

    public static MonitorDto toDto(Monitor monitor) {
        return MonitorDto.builder()
                .id(monitor.getId())
                .email(monitor.getEmail())
                .firstName(monitor.getFirstName())
                .lastName(monitor.getLastName())
                .companyName(monitor.getCompanyName())
                .registrationDate(monitor.getRegistrationDate())
                .phoneNumber(monitor.getPhoneNumber())
                .build();
    }

}