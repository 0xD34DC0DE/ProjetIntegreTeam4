package com.team4.backend.mapping;

import com.team4.backend.dto.StudentDto;
import com.team4.backend.model.Student;

public abstract class StudentMapper {

    public static Student toEntity(StudentDto studentDto) {
        return Student.studentBuilder()
                .id(studentDto.getId())
                .registrationNumber(studentDto.getRegistrationNumber())
                .email(studentDto.getEmail())
                .password(studentDto.getPassword())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .registrationDate(studentDto.getRegistrationDate())
                .schoolName(studentDto.getSchoolName())
                .phoneNumber(studentDto.getPhoneNumber())
                .studentState(studentDto.getStudentState())
                .build();
    }

    public static StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .registrationNumber(student.getRegistrationNumber())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .registrationDate(student.getRegistrationDate())
                .schoolName(student.getSchoolName())
                .phoneNumber(student.getPhoneNumber())
                .studentState(student.getStudentState())
                .build();
    }

}
