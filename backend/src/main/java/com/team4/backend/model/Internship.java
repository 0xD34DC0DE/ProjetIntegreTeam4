package com.team4.backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(collection = "internships")
public class Internship {

    @Id
    private String id;

    private String monitorEmail;

    private String internshipManagerEmail;

    private String studentEmail;

    private InternshipContract internshipContract;

    private LocalDate beginningDate;

    private LocalDate endingDate;

    @Builder
    public Internship(String id,
                      String monitorEmail,
                      String internshipManagerEmail,
                      String studentEmail,
                      InternshipContract internshipContract,
                      LocalDate beginningDate,
                      LocalDate endingDate) {
        this.id = id;
        this.monitorEmail = monitorEmail;
        this.internshipManagerEmail = internshipManagerEmail;
        this.studentEmail = studentEmail;
        this.internshipContract = internshipContract;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
    }
}
