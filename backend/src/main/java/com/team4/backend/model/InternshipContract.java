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

    private String internshipOfferId;

    private String address;

    private LocalDate beginningDate;

    private LocalDate endingDate;

    private String dailySchedule;

    private Float hoursPerWeek;

    private Float hourlyRate;

    private String internTasks;

    private Signature studentSignature;

    private Signature monitorSignature;

    private Signature internshipManagerSignature;

    @Builder
    public InternshipContract(String id,
                              String internshipOfferId,
                              String address,
                              LocalDate beginningDate,
                              LocalDate endingDate,
                              String dailySchedule,
                              Float hourlyRate,
                              Float hoursPerWeek,
                              String internTasks,
                              Signature studentSignature,
                              Signature monitorSignature,
                              Signature internshipManagerSignature) {
        this.id = id;
        this.internshipOfferId = internshipOfferId;
        this.address = address;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.dailySchedule = dailySchedule;
        this.hoursPerWeek = hoursPerWeek;
        this.hourlyRate = hourlyRate;
        this.internTasks = internTasks;
        this.studentSignature = studentSignature;
        this.monitorSignature = monitorSignature;
        this.internshipManagerSignature = internshipManagerSignature;
    }

    public boolean hasUserSigned(String userId) {
        if (userId.equals(studentSignature.getUserId()))
            return studentSignature.getHasSigned();

        if (userId.equals(internshipManagerSignature.getUserId()))
            return internshipManagerSignature.getHasSigned();

        if (userId.equals(monitorSignature.getUserId()))
            return monitorSignature.getHasSigned();

        return false;
    }

    public boolean isUserInContract(String userId) {
        if (userId.equals(studentSignature.getUserId()))
            return true;

        if (userId.equals(internshipManagerSignature.getUserId()))
            return true;

        if (userId.equals(monitorSignature.getUserId()))
            return true;

        return false;
    }
}
