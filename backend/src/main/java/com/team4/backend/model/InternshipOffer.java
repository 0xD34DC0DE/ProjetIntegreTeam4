package com.team4.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "internshipOffers")
public class InternshipOffer implements Serializable {

    @Id
    private String id;

    private String title;

    private LocalDate limitDateToApply;

    private LocalDate beginningDate;

    private LocalDate endingDate;

    private String monitorEmail;

    private String companyName;

    private String description;

    private Float minSalary;

    private Float maxSalary;

    private Boolean isValidated;

    private LocalDateTime validationDate    ;

    private Boolean isExclusive;

    private String emailOfApprovingInternshipManager;

    private Set<String> listEmailInterestedStudents;

}
