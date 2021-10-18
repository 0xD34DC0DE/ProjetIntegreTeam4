package com.team4.backend.mapping;

import com.team4.backend.dto.InternshipManagerCreationDto;
import com.team4.backend.model.InternshipManager;
import com.team4.backend.testdata.InternshipManagerMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InternshipManagerMapperTest {

    @Test
    void mapEntityToDto() {
        //ARRANGE
        InternshipManager internshipManager = InternshipManagerMockData.GetInternshipManager();

        //ACT
        InternshipManagerCreationDto internshipManagerDto = InternshipManagerMapper.toDto(internshipManager);

        //ASSERT
        assertNull(internshipManagerDto.getPassword()); // Should not return password to frontend

        assertEquals(internshipManager.getId(), internshipManagerDto.getId());
        assertEquals(internshipManager.getEmail(), internshipManagerDto.getEmail());
        assertEquals(internshipManager.getFirstName(), internshipManagerDto.getFirstName());
        assertEquals(internshipManager.getLastName(), internshipManagerDto.getLastName());
        assertEquals(internshipManager.getPhoneNumber(), internshipManagerDto.getPhoneNumber());
        assertEquals(internshipManager.getRegistrationDate(), internshipManagerDto.getRegistrationDate());
    }

    @Test
    void mapDtoToEntity() {
        //ARRANGE
        InternshipManagerCreationDto internshipManagerDto = InternshipManagerMockData.GetInternshipManagerDto();

        //ACT
        InternshipManager internshipManager = InternshipManagerMapper.toEntity(internshipManagerDto);

        //ASSERT
        assertEquals(internshipManagerDto.getId(), internshipManager.getId());
        assertEquals(internshipManagerDto.getEmail(), internshipManager.getEmail());
        assertEquals(internshipManagerDto.getPassword(), internshipManager.getPassword());
        assertEquals(internshipManagerDto.getFirstName(), internshipManager.getFirstName());
        assertEquals(internshipManagerDto.getLastName(), internshipManager.getLastName());
        assertEquals(internshipManagerDto.getPhoneNumber(), internshipManager.getPhoneNumber());
        assertEquals(internshipManagerDto.getRegistrationDate(), internshipManager.getRegistrationDate());
    }

}
