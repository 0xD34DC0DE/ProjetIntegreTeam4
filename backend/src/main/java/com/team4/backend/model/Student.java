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
@Document(collection = "users")
public class Student extends User {

    private StudentState studentState;

    @Builder(builderMethodName = "studentBuilder")
    public Student(String id,
                   String email,
                   String firstName,
                   String lastName,
                   String password,
                   String phoneNumber,
                   StudentState studentState,
                   LocalDate registrationDate) {
        super(id,
                email,
                firstName,
                lastName,
                password,
                phoneNumber,
                Role.STUDENT,
                true,
                registrationDate);
        this.phoneNumber = phoneNumber;
        this.studentState = studentState;
    }

}
