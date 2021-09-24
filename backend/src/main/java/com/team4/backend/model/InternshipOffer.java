package com.team4.backend.model;

import com.team4.backend.dto.InternshipOfferDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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

    private LocalDate limitDateToApply, beginningDate, endingDate;

    private Monitor monitor;

    private String companyName, description;

    private Float minSalary,maxSalary;

    private boolean isValidated, isExclusive;

    private List<Student> listInterestedStudents, listExclusiveStudents;

    public InternshipOffer(InternshipOfferDTO internshipOfferDTO, Monitor monitor) {
        this.limitDateToApply = internshipOfferDTO.getLimitDateToApply();
        this.beginningDate = internshipOfferDTO.getBeginningDate();
        this.endingDate = internshipOfferDTO.getEndingDate();
        this.companyName = internshipOfferDTO.getCompanyName();
        this.description = internshipOfferDTO.getDescription();
        this.minSalary = internshipOfferDTO.getMinSalary();
        this.maxSalary = internshipOfferDTO.getMaxSalary();
        this.monitor = monitor;
    }
}
