package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class InternshipManager extends User {

    @Builder(builderMethodName = "internshipManagerBuilder")
    public InternshipManager(String id,
                             String email,
                             String firstName,
                             String lastName,
                             String password,
                             String registrationNumber,
                             String phoneNumber,
                             LocalDate registrationDate) {
        super(id,
                email,
                firstName,
                lastName,
                password,
                registrationNumber,
                phoneNumber,
                Role.INTERNSHIP_MANAGER,
                true,
                registrationDate);
    }
}
