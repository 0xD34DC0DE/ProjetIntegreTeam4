package com.team4.backend.mapping;

import com.team4.backend.dto.StudentDto;
import com.team4.backend.model.Student;

import java.time.LocalDate;

public abstract class StudentMapper {

    public static Student toEntity(StudentDto studentDto) {
        return Student.studentBuilder()
                .email(studentDto.getEmail())
                .password(studentDto.getPassword())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .registrationDate(LocalDate.now())
                .phoneNumber(studentDto.getPhoneNumber())
                .studentState(studentDto.getStudentState())
                .hasValidCv(false)
                .build();
    }

    public static StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .registrationDate(student.getRegistrationDate())
                .phoneNumber(student.getPhoneNumber())
                .studentState(student.getStudentState())
                .hasValidCv(student.getHasValidCv())
                .build();
    }

}
