package com.team4.backend.testdata;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.model.Supervisor;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public static SupervisorDetailsDto getMockSupervisorDto() {
        return SupervisorDetailsDto.builder()
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

    public static Flux<Supervisor> getAllSupervisors() {
        return Flux.just(Supervisor.supervisorBuilder()
                .id("123a45ce678ae91d0b111b21")
                .email("professeur@outlook.com")
                .password("prof123")
                .firstName("Maxime")
                .lastName("Dupuis")
                .studentEmails(getStudentEmails())
                .phoneNumber("514-111-2222")
                .registrationDate(null) // Current date
                .build(), Supervisor.supervisorBuilder()
                .id("222a44ce555ae66d0b777b88")
                .email("enseignant@outlook.com")
                .password("enseignant123")
                .firstName("Paul")
                .lastName("Hebert")
                .phoneNumber("514-222-1111")
                .registrationDate(null) // Current date
                .build());
    }

    public static Flux<SupervisorDetailsDto> getAllSupervisorsDto() {
        return Flux.just(SupervisorDetailsDto.builder()
                .id("123a45ce678ae91d0b111b21")
                .email("professeur@outlook.com")
                .password("prof123")
                .firstName("Maxime")
                .lastName("Dupuis")
                .studentEmails(getStudentEmails())
                .phoneNumber("514-111-2222")
                .registrationDate(null) // Current date
                .build(), SupervisorDetailsDto.builder()
                .id("222a44ce555ae66d0b777b88")
                .email("enseignant@outlook.com")
                .password("enseignant123")
                .firstName("Paul")
                .lastName("Hebert")
                .phoneNumber("514-222-1111")
                .registrationDate(null) // Current date
                .build());
    }

    public static Set<String> getStudentEmails() {
        return new HashSet<>(Arrays.asList("12395432@gmail.com", "toto23@outlook.com"));
    }

}
