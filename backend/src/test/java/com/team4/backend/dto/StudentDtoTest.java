package com.team4.backend.dto;

import com.team4.backend.testdata.StudentMockData;
import com.team4.backend.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDtoTest {

    @Test
    void convertStudentEntityToStudentDto_correct() {
        //ARANGE
        Student entity = StudentMockData.getMockStudent();

        //ACT
        StudentDto dto = StudentDto.entityToDto(entity);

        //ASSERT
        assertNull(dto.getPassword()); // password shouldn't be given to frontend

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getRegistrationNumber(), dto.getRegistrationNumber());
        assertEquals(entity.getSchoolName(), dto.getSchoolName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getStudentState(), dto.getStudentState());
        assertEquals(entity.getRegistrationDate(), dto.getRegistrationDate());
    }

    @Test
    void convertStudentDtoToStudentEntity_correct() {
        //ARANGE
        StudentDto dto = StudentMockData.getMockStudentDto();

        //ACT
        Student entity = StudentDto.dtoToEntity(dto);

        //ASSERT
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getRegistrationNumber(), entity.getRegistrationNumber());
        assertEquals(dto.getSchoolName(), entity.getSchoolName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getStudentState(), entity.getStudentState());
        assertEquals(dto.getRegistrationDate(), entity.getRegistrationDate());
    }
}
