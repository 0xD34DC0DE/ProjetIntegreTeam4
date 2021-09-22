package com.team4.backend.dto;

import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class InternshipOfferDTO implements Serializable {
    private String id;
    private LocalDate limitDateToApply,beginningDate,endingDate;
    private String companyName,emailOfMonitor,description;
    private List<String> listEmailInterestedStudents,listEmailExclusiveStudents;
    private boolean isValidated;

    public InternshipOfferDTO(InternshipOffer internshipOffer){
        this.id = internshipOffer.getId();
        this.limitDateToApply = internshipOffer.getLimitDateToApply();
        this.beginningDate = internshipOffer.getBeginningDate();
        this.endingDate = internshipOffer.getEndingDate();
        this.companyName = internshipOffer.getCompanyName();
        this.emailOfMonitor = internshipOffer.getMonitor().getEmail();
        this.description = internshipOffer.getDescription();
        this.listEmailInterestedStudents = internshipOffer.getListInterestedStudents().stream().map(User::getEmail).collect(Collectors.toList());
        this.listEmailExclusiveStudents = internshipOffer.getListExclusiveStudents().stream().map(User::getEmail).collect(Collectors.toList());
        this.isValidated = internshipOffer.isValidated();
    }
}
