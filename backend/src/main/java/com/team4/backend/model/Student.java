package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class Student extends User {

    private Boolean hasCv;

    private StudentState studentState;

    private Set<LocalDate> interviewsDate;

    private Boolean hasValidCv;

    private Set<String> appliedOffersId;

    private Set<String> exclusiveOffersId;

    private Set<LocalDate> evaluationsDates;

    @Builder(builderMethodName = "studentBuilder")
    public Student(String id,
                   String email,
                   String firstName,
                   String lastName,
                   String password,
                   String phoneNumber,
                   StudentState studentState,
                   LocalDate registrationDate,
                   Set<String> appliedOffersId,
                   Set<String> exclusiveOffersId,
                   Boolean hasValidCv,
                   Set<LocalDate> evaluationsDates,
                   Set<LocalDate> interviewsDate) {
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
        this.hasCv = false;
        this.evaluationsDates = evaluationsDates;
        this.interviewsDate = interviewsDate;
    }

}
