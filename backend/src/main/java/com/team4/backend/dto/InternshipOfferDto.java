package com.team4.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class InternshipOfferDto implements Serializable {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate limitDateToApply;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginningDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;

    private String emailOfMonitor;

    private String companyName;

    private String description;

    private Float minSalary, maxSalary;

    private boolean isValidated;

    private List<String> listEmailInterestedStudents;

    private List<String> listEmailExclusiveStudents;

    public InternshipOfferDto(InternshipOffer internshipOffer) {
        this.id = internshipOffer.getId();
        this.limitDateToApply = internshipOffer.getLimitDateToApply();
        this.beginningDate = internshipOffer.getBeginningDate();
        this.endingDate = internshipOffer.getEndingDate();
        this.companyName = internshipOffer.getCompanyName();
        this.emailOfMonitor = internshipOffer.getMonitor().getEmail();
        this.description = internshipOffer.getDescription();
        this.minSalary = internshipOffer.getMinSalary();
        this.maxSalary = internshipOffer.getMaxSalary();
        this.listEmailInterestedStudents = internshipOffer.getListInterestedStudents() == null ? Collections.emptyList() :
                internshipOffer.getListInterestedStudents().stream().map(User::getId).collect(Collectors.toList());
        this.listEmailExclusiveStudents = internshipOffer.getListExclusiveStudents() == null ? Collections.emptyList() :
                internshipOffer.getListExclusiveStudents().stream().map(User::getId).collect(Collectors.toList());
        this.isValidated = internshipOffer.isValidated();
    }

}
