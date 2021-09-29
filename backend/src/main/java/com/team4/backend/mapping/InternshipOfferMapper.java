package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipOfferDto;
import com.team4.backend.model.InternshipOffer;
import com.team4.backend.model.Monitor;
import com.team4.backend.model.User;

import java.util.Collections;
import java.util.stream.Collectors;

public class InternshipOfferMapper {

    public static InternshipOfferDto toDto(InternshipOffer internshipOffer) {
        return InternshipOfferDto.builder()
                .id(internshipOffer.getId())
                .limitDateToApply(internshipOffer.getLimitDateToApply())
                .beginningDate(internshipOffer.getBeginningDate())
                .endingDate(internshipOffer.getEndingDate())
                .companyName(internshipOffer.getCompanyName())
                .emailOfMonitor(internshipOffer.getMonitor().getEmail())
                .description(internshipOffer.getDescription())
                .minSalary(internshipOffer.getMinSalary())
                .maxSalary(internshipOffer.getMaxSalary())
                .listEmailInterestedStudents(internshipOffer.getListInterestedStudents() == null ? Collections.emptyList() :
                        internshipOffer.getListInterestedStudents().stream().map(User::getEmail).collect(Collectors.toList()))
                .listEmailExclusiveStudents(internshipOffer.getListExclusiveStudents() == null ? Collections.emptyList() :
                        internshipOffer.getListExclusiveStudents().stream().map(User::getEmail).collect(Collectors.toList()))
                .isValidated(internshipOffer.isValidated()).build();
    }

    public static InternshipOffer toEntity(InternshipOfferDto internshipOfferDto, Monitor monitor) {
        return InternshipOffer.builder()
                .id(internshipOfferDto.getId())
                .limitDateToApply(internshipOfferDto.getLimitDateToApply())
                .beginningDate(internshipOfferDto.getBeginningDate())
                .endingDate(internshipOfferDto.getEndingDate())
                .companyName(internshipOfferDto.getCompanyName())
                .monitor(monitor)
                .description(internshipOfferDto.getDescription())
                .minSalary(internshipOfferDto.getMinSalary())
                .maxSalary(internshipOfferDto.getMaxSalary())
                .build();
    }
}
