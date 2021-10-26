package com.team4.backend.mapping;

import com.team4.backend.dto.*;
import com.team4.backend.model.InternshipOffer;

import java.util.Collections;
import java.util.HashSet;

public abstract class InternshipOfferMapper {

    public static InternshipOffer toEntity(InternshipOfferCreationDto internshipOfferCreationDto) {
        return InternshipOffer.builder()
                .limitDateToApply(internshipOfferCreationDto.getLimitDateToApply())
                .title(internshipOfferCreationDto.getTitle())
                .beginningDate(internshipOfferCreationDto.getBeginningDate())
                .endingDate(internshipOfferCreationDto.getEndingDate())
                .companyName(internshipOfferCreationDto.getCompanyName())
                .monitorEmail(internshipOfferCreationDto.getMonitorEmail())
                .description(internshipOfferCreationDto.getDescription())
                .minSalary(internshipOfferCreationDto.getMinSalary())
                .maxSalary(internshipOfferCreationDto.getMaxSalary())
                .listEmailInterestedStudents(new HashSet<>())
                .isValidated(false)
                .isExclusive(false)
                .build();
    }

    public static InternshipOfferStudentViewDto toStudentViewDto(InternshipOffer internshipOffer) {
        return InternshipOfferStudentViewDto.internshipOfferStudentViewDtoBuilder()
                .id(internshipOffer.getId())
                .title(internshipOffer.getTitle())
                .limitDateToApply(internshipOffer.getLimitDateToApply())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .companyName(internshipOffer.getCompanyName())
                .description(internshipOffer.getDescription())
                .minSalary(internshipOffer.getMinSalary())
                .maxSalary(internshipOffer.getMaxSalary())
                .isExclusive(internshipOffer.getIsExclusive())
                .hasAlreadyApplied(false)
                .build();
    }

    public static InternshipOfferStudentInterestViewDto toStudentInterestViewDto(InternshipOffer internshipOffer) {
        return InternshipOfferStudentInterestViewDto.internshipOfferStudentInterestViewDtoBuilder()
                .id(internshipOffer.getId())
                .title(internshipOffer.getTitle())
                .companyName(internshipOffer.getCompanyName())
                .description(internshipOffer.getDescription())
                .build();
    }

    public static InternshipOfferDetailedDto toDto(InternshipOffer internshipOffer) {
        return InternshipOfferDetailedDto.internshipOfferDetailedDtoBuilder()
                .id(internshipOffer.getId())
                .title(internshipOffer.getTitle())
                .limitDateToApply(internshipOffer.getLimitDateToApply())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .companyName(internshipOffer.getCompanyName())
                .description(internshipOffer.getDescription())
                .minSalary(internshipOffer.getMinSalary())
                .maxSalary(internshipOffer.getMaxSalary())
                .build();
    }

    public static InternshipOfferMonitorViewDto toMonitorViewDto(InternshipOffer internshipOffer) {
        return InternshipOfferMonitorViewDto.internshipOfferMonitorViewDtoBuilder()
                .id(internshipOffer.getId())
                .title(internshipOffer.getTitle())
                .limitDateToApply(internshipOffer.getLimitDateToApply())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .companyName(internshipOffer.getCompanyName())
                .description(internshipOffer.getDescription())
                .minSalary(internshipOffer.getMinSalary())
                .maxSalary(internshipOffer.getMaxSalary())
                .listEmailInterestedStudents(internshipOffer.getListEmailInterestedStudents())
                .build();
    }

}
