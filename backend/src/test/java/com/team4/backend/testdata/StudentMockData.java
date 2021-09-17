package com.team4.backend.testdata;

import com.team4.backend.dto.StudentDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;

public class StudentMockData {

    public static Student getMockStudent() {
        return Student.studentBuilder()
                .id("mock_id")
                .registrationNumber("123456789")
                .email("123456789@gmail.com")
                .password("passwd")
                .firstName("John")
                .lastName("Doe")
                .schoolName("Example school")
                .phoneNumber("123-456-7890")
                .studentState(StudentState.REGISTERED)
                .registrationDate(null) // Current date
                .build();
    }

    public static StudentDto getMockStudentDto() {
        return StudentDto.builder()
                .id("mock_id")
                .registrationNumber("123456789")
                .email("123456789@gmail.com")
                .password("passwd")
                .firstName("John")
                .lastName("Doe")
                .schoolName("Example school")
                .phoneNumber("123-456-7890")
                .studentState(StudentState.REGISTERED)
                .registrationDate(null) // Current date
                .build();
    }

}