package com.team4.backend.model;

import com.team4.backend.dto.InternshipOfferDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
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

    private boolean isExclusive;

    private List<String> listEmailInterestedStudents;

    public InternshipOffer(String id,
                           LocalDate limitDateToApply,
                           LocalDate beginningDate,
                           LocalDate endingDate,
                           String companyName,
                           String description,
                           Float minSalary,
                           Float maxSalary,
                           boolean isValidated,
                           boolean isExclusive,
                           String emailOfMonitor) {
        this.id = id;
        this.limitDateToApply = limitDateToApply;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.companyName = companyName;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.isValidated = isValidated;
        this.isExclusive = isExclusive;
        this.emailOfMonitor = emailOfMonitor;
    }
}
