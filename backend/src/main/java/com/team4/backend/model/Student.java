package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Student extends User {

    private StudentState studentState;

    private Boolean hasValidCv;

    private List<String> appliedOffersId;

    private Set<String> exclusiveOffersId;

    @Builder(builderMethodName = "studentBuilder")
    public Student(String id,
                   String email,
                   String firstName,
                   String lastName,
                   String password,
                   String phoneNumber,
                   StudentState studentState,
                   LocalDate registrationDate,
                   List<String> appliedOffersId,
                   Set<String> exclusiveOffersId,
                   Boolean hasValidCv) {
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
        this.appliedOffersId = appliedOffersId;
        this.exclusiveOffersId = exclusiveOffersId;
        this.hasValidCv = hasValidCv;
    }

}
