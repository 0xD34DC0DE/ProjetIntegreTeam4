package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipDetailsDto;
import com.team4.backend.dto.InternshipCreationDto;
import com.team4.backend.dto.InternshipDto;
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
    void mapInternshipCreationDtoToEntity() {
        //ARRANGE
        InternshipCreationDto internshipCreationDto = InternshipMockData.getInternshipCreationDto();

        //ACT
        Internship internship = InternshipMapper.toEntity(internshipCreationDto);

        //ASSERT
        assertEquals(internshipCreationDto.getStudentEmail(), internship.getStudentEmail());
        assertEquals(internshipCreationDto.getInternshipManagerEmail(), internship.getInternshipManagerEmail());
        assertEquals(internshipCreationDto.getMonitorEmail(), internship.getMonitorEmail());
        assertEquals(internshipCreationDto.getStartDate(), internship.getBeginningDate());
        assertEquals(internshipCreationDto.getEndDate(), internship.getEndingDate());
    }

    @Test
    void mapEntityTDto() {
        //ARRANGE
        Internship internship = InternshipMockData.getInternship();

        //ACT
        InternshipDto internshipDto = InternshipMapper.toDto(internship);

        //ASSERT
        assertEquals(internshipDto.getStudentEmail(), internship.getStudentEmail());
        assertEquals(internshipDto.getInternshipManagerEmail(), internship.getInternshipManagerEmail());
        assertEquals(internshipDto.getMonitorEmail(), internship.getMonitorEmail());
        assertEquals(internshipDto.getStartDate(), internship.getBeginningDate());
        assertEquals(internshipDto.getEndDate(), internship.getEndingDate());
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
