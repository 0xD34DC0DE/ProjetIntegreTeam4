package com.team4.backend.mapping;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.model.Supervisor;
import com.team4.backend.model.TimestampedEntry;
import com.team4.backend.testdata.SupervisorMockData;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SupervisorMapperTest {

    @Test
    void mapDtoToEntity() {
        //ARRANGE
        SupervisorDetailsDto dto = SupervisorMockData.getMockSupervisorDto();

        dto.setStudentEmails(new HashSet<>());

        //ACT
        Supervisor entity = SupervisorMapper.toEntity(dto);

        //ASSERT
        assertNull(entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertTrue(dto.getStudentEmails().isEmpty());
        assertEquals(dto.getRegistrationDate(), entity.getRegistrationDate());
    }

    @Test
    void mapEntityToDto() {
        //ARANGE
        Supervisor entity = SupervisorMockData.getMockSupervisor();

        //ACT
        SupervisorDetailsDto dto = SupervisorMapper.toDetailsDto(entity);

        //ASSERT
        assertNull(dto.getPassword()); // password shouldn't be given to frontend

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getStudentTimestampedEntries().stream()
                .map(TimestampedEntry::getEmail)
                .collect(Collectors.toSet()), dto.getStudentEmails());
        assertEquals(entity.getRegistrationDate(), dto.getRegistrationDate());
    }

}
