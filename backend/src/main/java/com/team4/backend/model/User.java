package com.team4.backend.model;

import com.team4.backend.meta.ExcludeFromGeneratedCoverage;
import com.team4.backend.model.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@ToString
@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails, Serializable {

    @Id
    private String id;

    private String registrationNumber;

    private String email;

    @Setter
    private String password;

    private String firstName;

    private String lastName;

    private LocalDate registrationDate;

    private Role role;

    private Boolean isEnabled;

    @Builder
    public User(String id,
                String email,
                String firstName,
                String lastName,
                String password,
                String registrationNumber,
                Role role,
                Boolean isEnabled,
                LocalDate registrationDate){
        this.id = id; // Auto generated
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.role = role;
        this.isEnabled = isEnabled;
        this.registrationDate = Optional.ofNullable(registrationDate).orElse(LocalDate.now());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @ExcludeFromGeneratedCoverage
    public String getUsername() {
        return null;
    }

    @Override
    @ExcludeFromGeneratedCoverage
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @ExcludeFromGeneratedCoverage
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @ExcludeFromGeneratedCoverage
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
