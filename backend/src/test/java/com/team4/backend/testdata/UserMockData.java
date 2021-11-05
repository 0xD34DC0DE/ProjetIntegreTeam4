package com.team4.backend.testdata;

import com.team4.backend.model.Student;
import com.team4.backend.model.User;
import com.team4.backend.model.enums.StudentState;
import reactor.core.publisher.Flux;

public abstract class UserMockData {

    public static Flux<User> getAllStudents() {
        return Flux.just(
                Student.studentBuilder()
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
}
