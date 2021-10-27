package com.team4.backend.mapping;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.StudentProfileDto;
import com.team4.backend.model.Student;
import com.team4.backend.testdata.StudentMockData;
import org.assertj.core.api.LocalDateAssert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StudentMapperTest {

    @Test
    void mapDtoToEntity() {
        //ARANGE
        StudentDetailsDto dto = StudentMockData.getMockStudentDto();

        //ACT
        Student entity = StudentMapper.toEntity(dto);

        //ASSERT
        assertNull(entity.getId());
        assertEquals(dto.getEmail(), entity.getEmail());
        assertEquals(dto.getPassword(), entity.getPassword());
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getStudentState(), entity.getStudentState());
        assertEquals(dto.getRegistrationDate(), entity.getRegistrationDate());
        assertEquals(dto.getHasValidCv(), entity.getHasValidCv());
    }

    @Test
    void mapEntityToDto() {
        //ARANGE
        Student entity = StudentMockData.getMockStudent();

        //ACT
        StudentDetailsDto dto = StudentMapper.toDto(entity);

        //ASSERT
        assertNull(dto.getPassword()); // password shouldn't be given to frontend

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getStudentState(), dto.getStudentState());
        assertEquals(entity.getRegistrationDate(), dto.getRegistrationDate());
        assertEquals(entity.getHasValidCv(), dto.getHasValidCv());
    }

    @Test
    void mapEntityToProfileDto() {
        //ARANGE
        LocalDate localDate = LocalDate.now();
        Student entity = StudentMockData.getMockStudent();

        //ACT
        StudentProfileDto dto = StudentMapper.toProfileDto(entity);

        //ASSERT
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getStudentState(), dto.getStudentState());
        assertEquals(entity.getRegistrationDate(), dto.getRegistrationDate());
        assertEquals(entity.getAppliedOffersId().size(), dto.getNbrOfAppliedOffers());
        assertEquals(entity.getExclusiveOffersId().size(), dto.getNbrOfExclusiveOffers());
        assertEquals(entity.getHasValidCv(), dto.getHasValidCv());
        assertEquals(localDate,dto.getRecentInterviewDate());
    }

}
