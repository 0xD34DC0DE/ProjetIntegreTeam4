package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InternshipContractCreationDto implements Serializable {

    private String internshipOfferId;

    private String studentEmail;

    @Builder
    public InternshipContractCreationDto(String internshipIdOffer, String studentEmail) {
        this.internshipOfferId = internshipIdOffer;
        this.studentEmail = studentEmail;
    }
}
