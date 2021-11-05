package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipDetailedDto;
import com.team4.backend.dto.InternshipDto;
import com.team4.backend.model.Internship;
import com.team4.backend.testdata.InternshipMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternshipMapperTest {

    @Test
    void mapInternshipDtoToEntity() {
        //ARRANGE
        InternshipDetailedDto internshipDetailedDto = InternshipMockData.getInternshipDetailedDto();

        //ACT
        Internship internship = InternshipMapper.toEntity(internshipDetailedDto);

        //ASSERT
        assertEquals(internshipDetailedDto.getStudentEmail(), internship.getStudentEmail());
        assertEquals(internshipDetailedDto.getInternshipManagerEmail(), internship.getInternshipManagerEmail());
        assertEquals(internshipDetailedDto.getMonitorEmail(), internship.getMonitorEmail());
        assertEquals(internshipDetailedDto.getStartDate(), internship.getBeginningDate());
        assertEquals(internshipDetailedDto.getEndDate(), internship.getEndingDate());
    }

    @Test
    void mapEntityToDetailedDto() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();

        //ACT
        InternshipDetailedDto internshipDetailedDto = InternshipMapper.toDetailedDto(internship);

        //ASSERT
        assertEquals(internshipDetailedDto.getStudentEmail(), internship.getStudentEmail());
        assertEquals(internshipDetailedDto.getInternshipManagerEmail(), internship.getInternshipManagerEmail());
        assertEquals(internshipDetailedDto.getMonitorEmail(), internship.getMonitorEmail());
        assertEquals(internshipDetailedDto.getStartDate(), internship.getBeginningDate());
        assertEquals(internshipDetailedDto.getEndDate(), internship.getEndingDate());
    }
}
