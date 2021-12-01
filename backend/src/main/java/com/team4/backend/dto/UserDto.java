package com.team4.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    protected String id;

    protected String email;

    protected String firstName;

    protected String lastName;

    protected String profileImageId;

}
