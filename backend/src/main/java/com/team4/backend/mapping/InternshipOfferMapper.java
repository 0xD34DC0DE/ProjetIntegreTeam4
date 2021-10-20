package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipOfferCreationDto;
import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.dto.InternshipOfferMonitorViewDto;
import com.team4.backend.dto.InternshipOfferStudentViewDto;
import com.team4.backend.model.InternshipOffer;

import java.util.Collections;
import java.util.HashSet;

public abstract class InternshipOfferMapper {

    public static InternshipOffer toEntity(InternshipOfferCreationDto internshipOfferCreationDto) {
        return InternshipOffer.builder()
                .limitDateToApply(internshipOfferCreationDto.getLimitDateToApply())
                .beginningDate(internshipOfferCreationDto.getBeginningDate())
                .endingDate(internshipOfferCreationDto.getEndingDate())
                .companyName(internshipOfferCreationDto.getCompanyName())
                .emailOfMonitor(internshipOfferCreationDto.getEmailOfMonitor())
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

    public static InternshipOfferDto toDto(InternshipOffer internshipOffer) {
        return InternshipOfferDto.builder()
                .id(internshipOffer.getId())
                .limitDateToApply(internshipOffer.getLimitDateToApply())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .companyName(internshipOffer.getCompanyName())
                .description(internshipOffer.getDescription())
                .minSalary(internshipOffer.getMinSalary())
                .maxSalary(internshipOffer.getMaxSalary())
                .build();
    }

    public static InternshipOfferMonitorViewDto toMonitorView(InternshipOffer internshipOffer) {
        return InternshipOfferMonitorViewDto.internshipOfferMonitorViewDtoBuilder()
                .id(internshipOffer.getId())
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
