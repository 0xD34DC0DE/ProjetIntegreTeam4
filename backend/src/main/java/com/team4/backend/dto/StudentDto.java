package com.team4.backend.dto;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends UserDto implements Serializable {

    private String schoolName;

    private StudentState studentState;

    @Builder
    public StudentDto(String id,
                      String registrationNumber,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      LocalDate registrationDate,
                      String schoolName,
                      String phoneNumber,
                      StudentState studentState) {
        super(id,
                registrationNumber,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.STUDENT);
        this.schoolName = schoolName;
        this.studentState = studentState;
    }

}
