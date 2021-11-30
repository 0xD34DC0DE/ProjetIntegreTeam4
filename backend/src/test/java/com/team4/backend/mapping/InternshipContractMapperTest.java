package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipContractCreationDto;
import com.team4.backend.dto.InternshipContractDto;
import com.team4.backend.model.InternshipContract;
import com.team4.backend.testdata.InternshipContractMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InternshipContractMapperTest {

    @Test
    void mapDtoToEntity() {
        // ARRANGE
        InternshipContractDto internshipContractDto = InternshipContractMockData.getInternshipContractDto();

        //ACT
        InternshipContract entity = InternshipContractMapper.toEntity(internshipContractDto);

        //ASSERT
        assertEquals(internshipContractDto.getContractId(), entity.getId());
        assertEquals(internshipContractDto.getInternshipOfferId(), entity.getInternshipOfferId());
        assertNotNull(entity.getInternshipManagerSignature());
        assertNotNull(entity.getStudentSignature());
        assertNotNull(entity.getMonitorSignature());
    }

    @Test
    void mapEntityToDto() {
        // ARRANGE
        InternshipContract internshipContract = InternshipContractMockData.getInternshipContract();

        //ACT
        InternshipContractDto dto = InternshipContractMapper.toDto(internshipContract);

        //ASSERT
        assertEquals(internshipContract.getId(), dto.getContractId());
        assertEquals(internshipContract.getInternshipOfferId(), dto.getInternshipOfferId());
    }

    @Test
    void mapCreationDtoToEntity() {
        // ARRANGE
        InternshipContractCreationDto internshipContractCreationDto =
                InternshipContractMockData.getInternshipContractCreationDto();

        //ACT
        InternshipContract entity = InternshipContractMapper.toEntity(internshipContractCreationDto);

        //ASSERT
        assertEquals(internshipContractCreationDto.getInternshipOfferId(), entity.getInternshipOfferId());
        assertEquals(internshipContractCreationDto.getAddress(), entity.getAddress());
        assertEquals(internshipContractCreationDto.getDailySchedule(), entity.getDailySchedule());
        assertEquals(internshipContractCreationDto.getHourlyRate(), entity.getHourlyRate());
        assertNull(entity.getId());
        assertNotNull(entity.getMonitorSignature());
        assertNotNull(entity.getStudentSignature());
        assertNotNull(entity.getInternshipManagerSignature());
    }

}
