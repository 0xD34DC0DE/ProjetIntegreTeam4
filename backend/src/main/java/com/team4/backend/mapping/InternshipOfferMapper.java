package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.model.InternshipOffer;

import java.util.Collections;

public abstract class InternshipOfferMapper {

    public static InternshipOffer toEntity(InternshipOfferDto internshipOfferDto) {
        return InternshipOffer.builder()
                .limitDateToApply(internshipOfferDto.getLimitDateToApply())
                .beginningDate(internshipOfferDto.getBeginningDate())
                .endingDate(internshipOfferDto.getEndingDate())
                .companyName(internshipOfferDto.getCompanyName())
                .emailOfMonitor(internshipOfferDto.getEmailOfMonitor())
                .description(internshipOfferDto.getDescription())
                .minSalary(internshipOfferDto.getMinSalary())
                .maxSalary(internshipOfferDto.getMaxSalary())
                .listEmailInterestedStudents(Collections.emptyList())
                .isValidated(false)
                .isExclusive(false)
                .build();
    }

    public static InternshipOfferDto toDto(InternshipOffer internshipOffer) {
        return InternshipOfferDto.builder()
                .id(internshipOffer.getId())
                .limitDateToApply(internshipOffer.getLimitDateToApply())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .companyName(internshipOffer.getCompanyName())
                .emailOfMonitor(internshipOffer.getEmailOfMonitor())
                .description(internshipOffer.getDescription())
                .minSalary(internshipOffer.getMinSalary())
                .maxSalary(internshipOffer.getMaxSalary())
                .listEmailInterestedStudents(internshipOffer.getListEmailInterestedStudents())
                .isValidated(internshipOffer.isValidated()).build();
    }

}
