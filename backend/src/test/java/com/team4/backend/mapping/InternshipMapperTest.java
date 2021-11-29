package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipDetailsDto;
import com.team4.backend.model.Internship;
import com.team4.backend.testdata.InternshipMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternshipMapperTest {

    @Test
    void mapInternshipDtoToEntity() {
        //ARRANGE
        InternshipDetailsDto internshipDetailsDto = InternshipMockData.getInternshipDetailedDto();

        //ACT
        Internship internship = InternshipMapper.toEntity(internshipDetailsDto);

        //ASSERT
        assertEquals(internshipDetailsDto.getStudentEmail(), internship.getStudentEmail());
        assertEquals(internshipDetailsDto.getInternshipManagerEmail(), internship.getInternshipManagerEmail());
        assertEquals(internshipDetailsDto.getMonitorEmail(), internship.getMonitorEmail());
        assertEquals(internshipDetailsDto.getStartDate(), internship.getBeginningDate());
        assertEquals(internshipDetailsDto.getEndDate(), internship.getEndingDate());
    }

    @Test
    void mapEntityToDetailedDto() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();

        //ACT
        InternshipDetailsDto internshipDetailsDto = InternshipMapper.toDetailedDto(internship);

        //ASSERT
        assertEquals(internshipDetailsDto.getStudentEmail(), internship.getStudentEmail());
        assertEquals(internshipDetailsDto.getInternshipManagerEmail(), internship.getInternshipManagerEmail());
        assertEquals(internshipDetailsDto.getMonitorEmail(), internship.getMonitorEmail());
        assertEquals(internshipDetailsDto.getStartDate(), internship.getBeginningDate());
        assertEquals(internshipDetailsDto.getEndDate(), internship.getEndingDate());
    }
}
