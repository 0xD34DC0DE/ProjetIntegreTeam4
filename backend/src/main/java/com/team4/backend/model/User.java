package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

@ToString
@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements Serializable {

    @Id
    protected String id;

    protected String registrationNumber;

    protected String email;

    protected String password;

    protected String firstName;

    protected String lastName;

    protected String phoneNumber;

    protected LocalDate registrationDate;

    protected Role role;

    protected Boolean isEnabled;

    @Builder
    public User(String id,
                String email,
                String firstName,
                String lastName,
                String password,
                String registrationNumber,
                String phoneNumber,
                Role role,
                Boolean isEnabled,
                LocalDate registrationDate) {
        this.id = id; // Auto generated
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isEnabled = isEnabled;
        this.registrationDate = Optional.ofNullable(registrationDate).orElse(LocalDate.now());
    }

}
