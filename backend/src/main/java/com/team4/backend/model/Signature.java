package com.team4.backend.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
public class Signature {

    private String userId;

    private Boolean hasSigned;

    private LocalDate signDate;

    @Builder(builderMethodName = "signatureBuild")
    public Signature(String userId, Boolean hasSigned, LocalDate signDate) {
        this.userId = userId;
        this.hasSigned = hasSigned;
        this.signDate = signDate;
    }
}
