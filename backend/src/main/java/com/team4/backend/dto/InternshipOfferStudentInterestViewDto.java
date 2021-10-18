package com.team4.backend.dto;

import com.team4.backend.model.Student;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class InternshipOfferStudentInterestViewDto extends InternshipOfferDto implements Serializable {

    private List<Student> interestedStudentList;

    @Builder(builderMethodName = "internshipOfferStudentInterestViewDtoBuilder")
    public InternshipOfferStudentInterestViewDto(String id,
                                                 String companyName,
                                                 String description,
                                                 List<Student> interestedStudentList) {
        super(id,
                companyName,
                description
        );
        this.interestedStudentList = interestedStudentList;
    }

}