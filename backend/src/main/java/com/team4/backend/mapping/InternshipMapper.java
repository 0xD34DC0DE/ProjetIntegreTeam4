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
                .startDate(internshipCreationDto.getStartDate())
                .endDate(internshipCreationDto.getEndDate())
                .internshipContract(new InternshipContract())
                .build();
    }

    public static InternshipDto toDto(Internship internship) {
        return InternshipDto.builder()
                .monitorEmail(internship.getMonitorEmail())
                .studentEmail(internship.getStudentEmail())
                .internshipManagerEmail(internship.getInternshipManagerEmail())
                .startDate(internship.getStartDate())
                .endDate(internship.getEndDate())
                .build();
    }

    public static InternshipDetailedDto toDetailedDto(Internship internship) {
        return InternshipDetailedDto.internshipDetailedDtoBuilder()
                .monitorEmail(internship.getMonitorEmail())
                .studentEmail(internship.getStudentEmail())
                .internshipManagerEmail(internship.getInternshipManagerEmail())
                .startDate(internship.getStartDate())
                .endDate(internship.getEndDate())
                .build();
    }

}
