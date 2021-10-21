package com.team4.backend.testdata;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.mapping.StudentMapper;
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
                .appliedOffersId(new HashSet<>())
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

    public static List<Student> getListStudent(int count) {
        List<Student> students = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            Student student = Student.studentBuilder()
                    .id("id_" + i)
                    .build();

            students.add(student);
        }

        return students;
    }

    public static Set<String> getListExclusiveOfferIds() {
        Set<String> exclusiveOffers = new HashSet<>();
        exclusiveOffers.add("exclusive_id_1");
        exclusiveOffers.add("exclusive_id_2");
        return  exclusiveOffers;
    }

}
