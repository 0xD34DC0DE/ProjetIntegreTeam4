package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipCreationDto;
import com.team4.backend.dto.InternshipDetailedDto;
import com.team4.backend.dto.InternshipDto;
import com.team4.backend.model.Internship;
import com.team4.backend.model.InternshipContract;

public abstract class InternshipMapper {

    public static Internship toEntity(InternshipDto internshipDto) {
        return Internship.builder()
                .monitorEmail(internshipDto.getMonitorEmail())
                .studentEmail(internshipDto.getStudentEmail())
                .internshipManagerEmail(internshipDto.getInternshipManagerEmail())
                .beginningDate(internshipDto.getStartDate())
                .endingDate(internshipDto.getEndDate())
                .internshipContract(new InternshipContract())
                .build();
    }

    public static InternshipDto toDto(Internship internship) {
        return InternshipDto.builder()
                .monitorEmail(internship.getMonitorEmail())
                .studentEmail(internship.getStudentEmail())
                .internshipManagerEmail(internship.getInternshipManagerEmail())
                .startDate(internship.getBeginningDate())
                .endDate(internship.getEndingDate())
                .build();
    }

    public static InternshipDetailedDto toDetailedDto(Internship internship) {
        return InternshipDetailedDto.internshipDetailedDtoBuilder()
                .monitorEmail(internship.getMonitorEmail())
                .studentEmail(internship.getStudentEmail())
                .internshipManagerEmail(internship.getInternshipManagerEmail())
                .startDate(internship.getBeginningDate())
                .endDate(internship.getEndingDate())
                .build();
    }

}
