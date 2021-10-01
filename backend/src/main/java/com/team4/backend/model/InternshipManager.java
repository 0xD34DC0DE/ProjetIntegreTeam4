package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
                             String phoneNumber,
                             LocalDate registrationDate) {
        super(id,
                email,
                firstName,
                lastName,
                password,
                phoneNumber,
                Role.INTERNSHIP_MANAGER,
                true,
                registrationDate);
    }
}
