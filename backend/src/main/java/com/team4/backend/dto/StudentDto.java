package com.team4.backend.dto;

import com.team4.backend.model.Student;
import com.team4.backend.model.enums.Role;
import com.team4.backend.model.enums.StudentState;
import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends UserDto {

    private String schoolName;

    private String phoneNumber;

    private StudentState studentState;

    @Builder
    public StudentDto(String id,
                      String registrationNumber,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      LocalDate registrationDate,
                      Role role,
                      String schoolName,
                      String phoneNumber,
                      StudentState studentState) {
        super(id, registrationNumber, email, password, firstName, lastName, registrationDate,phoneNumber, role);
        this.schoolName = schoolName;
        this.phoneNumber = phoneNumber;
        this.studentState = studentState;
    }

    public static Student dtoToEntity(StudentDto studentDto) {
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

    public static StudentDto entityToDto(Student student) {
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
