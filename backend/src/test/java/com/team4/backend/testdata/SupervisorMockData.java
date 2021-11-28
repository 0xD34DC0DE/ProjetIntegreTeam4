package com.team4.backend.testdata;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SupervisorMockData {

    public static Supervisor getMockSupervisor() {
        return Supervisor.supervisorBuilder()
                .id("615a32ce577ae63d7b159b17")
                .email("jonathan_22@outlook.com")
                .password("soleil31@")
                .firstName("Jonathan")
                .lastName("Poulin")
                .studentTimestampedEntries(getTimeStampedEntries())
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
                .studentEmails(getTimeStampedEntries().stream()
                        .map(TimestampedEntry::getEmail)
                        .collect(Collectors.toCollection(TreeSet::new)))
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
                .studentTimestampedEntries(getTimeStampedEntries())
                .phoneNumber("514-111-2222")
                .registrationDate(null) // Current date
                .build(), Supervisor.supervisorBuilder()
                .id("222a44ce555ae66d0b777b88")
                .email("enseignant@outlook.com")
                .password("enseignant123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("514-222-1111")
                .registrationDate(null)
                .studentTimestampedEntries(getStudentEmailsTimeStampedEntries())// Current date
                .build());
    }

    public static List<Supervisor> getAllSupervisorsList() {
        return List.of(Supervisor.supervisorBuilder()
                .id("123a45ce678ae91d0b111b21")
                .email("professeur@outlook.com")
                .password("prof123")
                .firstName("Maxime")
                .lastName("Dupuis")
                .studentTimestampedEntries(getStudentEmailsTimeStampedEntries())
                .phoneNumber("514-111-2222")
                .registrationDate(null)
                .studentTimestampedEntries(getStudentEmailsTimeStampedEntries())
                .build(), Supervisor.supervisorBuilder()
                .id("222a44ce555ae66d0b777b88")
                .email("enseignant@outlook.com")
                .password("enseignant123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("514-222-1111")
                .registrationDate(null)
                .studentTimestampedEntries(getStudentEmailsTimeStampedEntries())
                .build());
    }

    public static Flux<SupervisorDetailsDto> getAllSupervisorsDto() {
        return Flux.just(SupervisorDetailsDto.builder()
                .id("123a45ce678ae91d0b111b21")
                .email("professeur@outlook.com")
                .password("prof123")
                .firstName("Maxime")
                .lastName("Dupuis")
                .studentEmails(getTimeStampedEntries().stream()
                        .map(TimestampedEntry::getEmail)
                        .collect(Collectors.toSet()))
                .phoneNumber("514-111-2222")
                .registrationDate(null) // Current date
                .build(), SupervisorDetailsDto.builder()
                .id("222a44ce555ae66d0b777b88")
                .email("enseignant@outlook.com")
                .password("enseignant123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("514-222-1111")
                .registrationDate(null)
                .studentEmails(getStudentEmails())// Current date
                .build());
    }

    public static Flux<Supervisor> getAllSupervisorsUpdated() {
        return Flux.just(Supervisor.supervisorBuilder()
                .id("123a45ce678ae91d0b111b21")
                .email("professeur@outlook.com")
                .password("prof123")
                .firstName("Maxime")
                .lastName("Dupuis")
                .studentTimestampedEntries(getStudentEmailsTimeStampedEntries())
                .phoneNumber("514-111-2222")
                .registrationDate(null) // Current date
                .build(), Supervisor.supervisorBuilder()
                .id("222a44ce555ae66d0b777b88")
                .email("enseignant@outlook.com")
                .password("enseignant123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("514-222-1111")
                .registrationDate(null)
                .studentTimestampedEntries(getStudentEmailsTimeStampedEntries())// Current date
                .build());
    }

    public static Set<String> getStudentEmails() {
        return new HashSet<>(Arrays.asList("12395432@gmail.com", "3643283423@gmail.com"));
    }

    public static Set<TimestampedEntry> getStudentEmailsTimeStampedEntries() {
        return new HashSet<>(Arrays.asList(
                new TimestampedEntry("12395432@gmail.com", LocalDateTime.now()),
                new TimestampedEntry("3643283423@gmail.com", LocalDateTime.now())));
    }
    
    public static Set<TimestampedEntry> getTimeStampedEntries() {
        return new HashSet<>(Arrays.asList(
                new TimestampedEntry("12395432@gmail.com", LocalDateTime.now()),
                new TimestampedEntry("toto23@outlook.com", LocalDateTime.now())));
    }

}
