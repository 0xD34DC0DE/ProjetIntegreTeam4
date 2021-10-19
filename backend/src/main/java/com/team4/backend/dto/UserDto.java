package com.team4.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    protected String id;

    protected String email;

    protected String firstName;

    protected String lastName;


}
