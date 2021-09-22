package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document("monitors")
@EqualsAndHashCode(callSuper = true)
public class Monitor extends User implements Serializable {

    @Builder(builderMethodName = "monitorBuilder")

    public Monitor(String id, String email, String firstName, String lastName, String password, String registrationNumber, String phoneNumber, Role role, Boolean isEnabled, LocalDate registrationDate) {
        super(id, email, firstName, lastName, password, registrationNumber, phoneNumber, role, isEnabled, registrationDate);
    }
}
