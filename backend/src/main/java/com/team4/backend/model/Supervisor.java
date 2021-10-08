package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Document("users")
@EqualsAndHashCode(callSuper = true)
public class Supervisor extends User implements Serializable {

    private List<String> studentEmails;

    @Builder(builderMethodName = "supervisorBuilder")
    public Supervisor(String id,
                      String email,
                      String firstName,
                      String lastName,
                      List<String> studentEmails,
                      String password,
                      String phoneNumber,
                      LocalDate registrationDate) {
        super(id,
                email,
                firstName,
                lastName,
                password,
                phoneNumber,
                Role.SUPERVISOR,
                true,
                registrationDate);
        this.studentEmails = studentEmails;
    }

}