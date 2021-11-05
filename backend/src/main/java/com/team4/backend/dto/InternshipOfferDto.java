package com.team4.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class InternshipOfferDto implements Serializable {

    private String id;

    private String title;

    private String companyName;

    private String description;

    @Builder(builderMethodName = "internshipOfferDtoBuilder")
    public InternshipOfferDto(String id,
                              String title,
                              String companyName,
                              String description) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.description = description;
    }

}
