package com.team4.backend.model;

import com.team4.backend.model.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails, Serializable {

    @Id
    @Getter
    private String id;

    @Getter @Setter
    private String registrationNumber,email,password,firstName,lastName;

    @Getter @Setter
    private LocalDate registrationDate;

    @Getter @Setter
    private Role role;

    @Getter @Setter
    private Boolean isEnabled;

    @Builder
    public User(String email,String firstName,String lastName,String password,String registrationNumber,Role role){
        this.id = null; // Auto generated
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationNumber = registrationNumber;
        this.role = role;
        this.registrationDate = LocalDate.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
