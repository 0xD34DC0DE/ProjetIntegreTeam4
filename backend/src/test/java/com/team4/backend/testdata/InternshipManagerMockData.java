package com.team4.backend.testdata;

import com.team4.backend.dto.InternshipManagerDetailsDto;
import com.team4.backend.model.InternshipManager;

public abstract class InternshipManagerMockData {

    public static InternshipManager GetInternshipManager() {
        return InternshipManager.internshipManagerBuilder()
                .id("internship_manager_id")
                .email("intership.manager@email.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .registrationDate(null) // Autogenerated (creation time) when null
                .build();
    }

    public static InternshipManagerDetailsDto GetInternshipManagerDto() {
        return InternshipManagerDetailsDto.builder()
                .id("internship_manager_id")
                .email("intership.manager@email.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .registrationDate(null) // Autogenerated (creation time) when null
                .build();
    }

}
