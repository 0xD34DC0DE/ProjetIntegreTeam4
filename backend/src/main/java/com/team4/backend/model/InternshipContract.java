package com.team4.backend.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "contracts")
public class InternshipContract {

    @Id
    private String id;

    private String address;

    private LocalDate startDate;

    private LocalDate endDate;

    private String dailySchedule;

    private Float hourlyRate;

    private String internTasks;

    private Signature studentSignature;

    private Signature monitorSignature;

    private Signature internshipManagerSignature;

    @Builder
    public InternshipContract(String id,
                              String address,
                              LocalDate startDate,
                              LocalDate endDate,
                              String dailySchedule,
                              Float hourlyRate,
                              String internTasks,
                              Signature studentSignature,
                              Signature monitorSignature,
                              Signature internshipManagerSignature) {
        this.id = id;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailySchedule = dailySchedule;
        this.hourlyRate = hourlyRate;
        this.internTasks = internTasks;
        this.studentSignature = studentSignature;
        this.monitorSignature = monitorSignature;
        this.internshipManagerSignature = internshipManagerSignature;
    }
}
