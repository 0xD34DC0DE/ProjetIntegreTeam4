package com.team4.backend.testdata;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;

import java.util.*;

public abstract class StudentMockData {

    public static Student getMockStudent() {
        return Student.studentBuilder()
                .id("mock_id")
                .email("123456789@gmail.com")
                .password("passwd")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123-456-7890")
                .studentState(StudentState.REGISTERED)
                .hasValidCv(false)
                .registrationDate(null) // Autogenerated (creation time) when null
                .exclusiveOffersId(getListExclusiveOfferIds())
                .build();
    }

    public static StudentDetailsDto getMockStudentDto() {
        return StudentDetailsDto.builder()
                .id("mock_id")
                .email("123456789@gmail.com")
                .password("passwd")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123-456-7890")
                .studentState(StudentState.REGISTERED)
                .hasValidCv(false)
                .registrationDate(null) // Autogenerated (creation time) when null
                .build();
    }

    public static Set<String> getListExclusiveOfferIds() {
        return Set.of("exclusive_id_1", "exclusive_id_2");
    }

}
