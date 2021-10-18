package com.team4.backend.mapping;

import com.team4.backend.dto.SupervisorDetailsDto;
import com.team4.backend.model.Supervisor;
import com.team4.backend.testdata.SupervisorMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SupervisorMapperTest {

    @Test
    void mapDtoToEntity() {
        //ARANGE
        SupervisorDetailsDto dto = SupervisorMockData.getMockSupervisorDto();

        //ACT
        Supervisor entity = SupervisorMapper.toEntity(dto);

        //ASSERT
        assertNull(entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getStudentEmails(), entity.getStudentEmails());
        assertEquals(dto.getRegistrationDate(), entity.getRegistrationDate());
    }

    @Test
    void mapEntityToDto() {
        //ARANGE
        Supervisor entity = SupervisorMockData.getMockSupervisor();

        //ACT
        SupervisorDetailsDto dto = SupervisorMapper.toDto(entity);

        //ASSERT
        assertNull(dto.getPassword()); // password shouldn't be given to frontend

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getStudentEmails(), dto.getStudentEmails());
        assertEquals(entity.getRegistrationDate(), dto.getRegistrationDate());
    }

}