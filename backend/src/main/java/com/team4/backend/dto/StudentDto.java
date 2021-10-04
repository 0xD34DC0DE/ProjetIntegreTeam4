package com.team4.backend.dto;

import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends UserDto implements Serializable {

    private StudentState studentState;

    @Builder
    public StudentDto(String id,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      LocalDate registrationDate,
                      String phoneNumber,
                      StudentState studentState) {
        super(id,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.STUDENT);
        this.studentState = studentState;
    }

}
