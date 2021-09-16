package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends UserDto {

    private String schoolName;

    private String phoneNumber;

    private StudentState studentState;

    @Builder
    public StudentDto(String id,
                      String registrationNumber,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      LocalDate registrationDate,
                      Role role,
                      String schoolName,
                      String phoneNumber,
                      StudentState studentState) {
        super(id, registrationNumber, email, password, firstName, lastName, registrationDate, role);
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.studentState = studentState;
    }
}
