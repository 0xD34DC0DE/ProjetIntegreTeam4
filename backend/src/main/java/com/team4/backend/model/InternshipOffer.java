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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "internshipOffers")
public class InternshipOffer implements Serializable {

    @Id
    private String id;

    private LocalDate limitDateToApply;

    private LocalDate beginningDate;

    private LocalDate endingDate;

    private String emailOfMonitor;

    private String companyName;

    private String description;

    private Float minSalary;

    private Float maxSalary;

    private boolean isValidated;

    private LocalDateTime validationDate;

    private Boolean isExclusive;

    private List<String> listEmailInterestedStudents;

}
