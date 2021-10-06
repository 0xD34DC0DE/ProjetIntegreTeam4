package com.team4.backend.dto;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupervisorDto extends UserDto implements Serializable {

    private List<Student> students;

    @Builder
    public SupervisorDto(String id,
                         String email,
                         String password,
                         String firstName,
                         String lastName,
                         List<Student> students,
                         LocalDate registrationDate,
                         String phoneNumber) {
        super(id,
                email,
                password,
                firstName,
                lastName,
                registrationDate,
                phoneNumber,
                Role.SUPERVISOR);
        this.students = students;
    }

}