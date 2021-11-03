package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipCreationDto;
import com.team4.backend.dto.InternshipDetailedDto;
import com.team4.backend.dto.InternshipDto;
import com.team4.backend.model.Internship;
import com.team4.backend.model.InternshipContract;

public abstract class InternshipMapper {

    public static Internship toEntity(InternshipCreationDto internshipCreationDto) {
        return Internship.builder()
                .monitorEmail(internshipCreationDto.getMonitorEmail())
                .studentEmail(internshipCreationDto.getStudentEmail())
                .internshipManagerEmail(internshipCreationDto.getInternshipManagerEmail())
                .beginningDate(internshipCreationDto.getStartDate())
                .beginningDate(internshipCreationDto.getEndDate())
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
