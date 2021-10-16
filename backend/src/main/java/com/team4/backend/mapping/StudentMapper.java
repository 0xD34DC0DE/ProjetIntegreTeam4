package com.team4.backend.mapping;

import com.team4.backend.dto.StudentCreationDto;
import com.team4.backend.model.Student;

import java.time.LocalDate;

public abstract class StudentMapper {

    public static Student toEntity(StudentCreationDto studentCreationDto) {
        return Student.studentBuilder()
                .email(studentCreationDto.getEmail())
                .password(studentCreationDto.getPassword())
                .firstName(studentCreationDto.getFirstName())
                .lastName(studentCreationDto.getLastName())
                .registrationDate(LocalDate.now())
                .phoneNumber(studentCreationDto.getPhoneNumber())
                .studentState(studentCreationDto.getStudentState())
                .hasValidCv(false)
                .build();
    }

    public static StudentCreationDto toDto(Student student) {
        return StudentCreationDto.builder()
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
