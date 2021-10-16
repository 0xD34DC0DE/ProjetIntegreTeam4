package com.team4.backend.mapping;

import com.team4.backend.dto.MonitorCreationDto;
import com.team4.backend.model.Monitor;
import com.team4.backend.testdata.MonitorMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MonitorMapperTest {

    @Test
    void mapEntityToDto() {
        //ARRANGE
        Monitor entity = MonitorMockData.getMockMonitor();

        //ACT
        MonitorCreationDto dto = MonitorMapper.toDto(entity);

        //ASSERT
        assertNull(dto.getPassword());

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getCompanyName(), dto.getCompanyName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getRegistrationDate(), dto.getRegistrationDate());
    }

    @Test
    void mapDtoToEntity() {
        //ARRANGE
        MonitorCreationDto dto = MonitorMockData.getMockMonitorDto();

        //ACT
        Monitor entity = MonitorMapper.toEntity(dto);

        //ASSERT
        assertNull(entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getCompanyName(), entity.getCompanyName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getRegistrationDate(), entity.getRegistrationDate());
    }

}
