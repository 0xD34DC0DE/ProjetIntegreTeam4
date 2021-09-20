package com.team4.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "internshipOffers")
public class InternshipOffer implements Serializable {

    private UUID uuid;
    private LocalDate limitDateToApply,beginningDate,endingDate;
    private String companyName,emailOfMonitor,description;
    private boolean isValidated,isExclusive;
    private List<Student> listInterestedStudents,listExclusiveStudents;
}
