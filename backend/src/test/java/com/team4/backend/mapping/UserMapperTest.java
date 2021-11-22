package com.team4.backend.mapping;

import com.team4.backend.dto.UserDto;
import com.team4.backend.model.User;
import com.team4.backend.testdata.UserMockData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {

    @Test
    void mapDtoToEntity() {
        //ARRANGE
        UserDto dto = UserMockData.getUserDto();

        //ACT
        User entity = UserMapper.toEntity(dto);

        //ASSERT
        assertNull(entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
    }
}
