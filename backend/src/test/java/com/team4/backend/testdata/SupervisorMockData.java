package com.team4.backend.testdata;

import com.team4.backend.dto.SupervisorDto;
import com.team4.backend.model.Supervisor;

import java.util.Arrays;
import java.util.List;

public class SupervisorMockData {

    public static Supervisor getMockSupervisor() {
        return Supervisor.supervisorBuilder()
                .id("615a32ce577ae63d7b159b17")
                .email("jonathan_22@outlook.com")
                .password("soleil31@")
                .firstName("Jonathan")
                .lastName("Poulin")
                .studentEmails(getStudentEmails())
                .phoneNumber("438-999-1234")
                .registrationDate(null) // Current date
                .build();
    }

    public static SupervisorDto getMockSupervisorDto() {
        return SupervisorDto.builder()
                .id("615a32ce577ae63d7b159b17")
                .email("jonathan_22@outlook.com")
                .password("soleil31@")
                .firstName("Jonathan")
                .lastName("Poulin")
                .studentEmails(getStudentEmails())
                .phoneNumber("438-999-1234")
                .registrationDate(null) // Current date
                .build();
    }

    public static List<String> getStudentEmails() {
        return Arrays.asList("12395432@gmail.com", "toto23@outlook.com");
    }
}