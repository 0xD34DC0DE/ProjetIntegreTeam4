package com.team4.backend.testdata;

import com.team4.backend.dto.StudentCreationDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import reactor.core.publisher.Flux;

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

    public static StudentCreationDto getMockStudentDto() {
        return StudentCreationDto.builder()
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

    public static Flux<Student> getAllStudents(){
        return Flux.just(Student.studentBuilder()
                .id("1234567890fgh")
                .email("student@gmail.com")
                .password("mdp")
                .firstName("Mario")
                .lastName("Bros")
                .phoneNumber("514-123-1234")
                .studentState(StudentState.REGISTERED)
                .hasValidCv(false)
                .registrationDate(null) // Autogenerated (creation time) when null
                .build(),
                Student.studentBuilder()
                        .id("000111222abc")
                        .email("eleve@gmail.com")
                        .password("mdpEleve")
                        .firstName("Luigi")
                        .lastName("Bros")
                        .phoneNumber("514-444-1235")
                        .studentState(StudentState.REGISTERED)
                        .hasValidCv(false)
                        .registrationDate(null) // Autogenerated (creation time) when null
                        .build());
    }

    public static Set<String> getListExclusiveOfferIds() {
        return Set.of("exclusive_id_1", "exclusive_id_2");
    }

}
