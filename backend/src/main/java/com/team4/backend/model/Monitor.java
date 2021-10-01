package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@Document("users")
@EqualsAndHashCode(callSuper = true)
public class Monitor extends User implements Serializable {

    private String companyName;

    @Builder(builderMethodName = "monitorBuilder")
    public Monitor(String id,
                   String email,
                   String firstName,
                   String lastName,
                   String password,
                   String registrationNumber,
                   String phoneNumber,
                   String companyName,
                   LocalDate registrationDate) {
        super(id,
                email,
                firstName,
                lastName,
                password,
                registrationNumber,
                phoneNumber,
                Role.MONITOR,
                true,
                registrationDate);
        this.companyName = companyName;
    }
}
