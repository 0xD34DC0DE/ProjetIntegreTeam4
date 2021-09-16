package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


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
                   StudentState studentState) {
        super(id, email, firstName, lastName, password, registrationNumber, Role.STUDENT);
        super.setIsEnabled(true);
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.studentState = studentState;
    }
}