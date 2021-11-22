package com.team4.backend.mapping;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.UserDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.User;

public abstract class UserMapper {

    public static Student toEntity(UserDto userDto) {
        return Student.studentBuilder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }

    public static StudentDetailsDto toDto(User user) {
        return StudentDetailsDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
