package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "students")
public class Student extends User {

    private String schoolName;

    private String phoneNumber;

    private StudentState studentState;

    @Builder(builderMethodName = "studentBuilder")
    public Student(String id,
                   String email,
                   String firstName,
                   String lastName,
                   String password,
                   String registrationNumber,
                   String schoolName,
                   String phoneNumber,
                   StudentState studentState,
                   LocalDate registrationDate) {
        super(id,
                email,
                firstName,
                lastName,
                password,
                registrationNumber,
                Role.STUDENT,
                true,
                registrationDate);
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.studentState = studentState;
    }
}
