package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InternshipContractDto {

    private String contractId;

    private String internshipOfferId;

    private String studentEmail;

    @Builder
    public InternshipContractDto(String internshipOfferId, String studentEmail, String contractId) {
        this.contractId = contractId;
        this.internshipOfferId = internshipOfferId;
        this.studentEmail = studentEmail;
    }
}
