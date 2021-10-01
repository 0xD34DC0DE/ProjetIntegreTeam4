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

    private Monitor monitor;

    private String companyName;

    private String description;

    private Float minSalary;

    private Float maxSalary;

    private boolean isValidated;

    private boolean isExclusive;

    private List<Student> listInterestedStudents;

    private List<Student> listExclusiveStudents;

    public InternshipOffer(String id,
                           LocalDate limitDateToApply,
                           LocalDate beginningDate,
                           LocalDate endingDate,
                           Monitor monitor,
                           String companyName,
                           String description,
                           Float minSalary,
                           Float maxSalary,
                           boolean isValidated,
                           boolean isExclusive) {
        this.id = id;
        this.limitDateToApply = limitDateToApply;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.monitor = monitor;
        this.companyName = companyName;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.isValidated = isValidated;
        this.isExclusive = isExclusive;
    }
}
