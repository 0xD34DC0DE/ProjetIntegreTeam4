package com.team4.backend.mapping;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.StudentProfileDto;
import com.team4.backend.model.Student;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public abstract class StudentMapper {

    public static Student toEntity(StudentDetailsDto studentCreationDto) {
        return Student.studentBuilder()
                .email(studentCreationDto.getEmail())
                .password(studentCreationDto.getPassword())
                .firstName(studentCreationDto.getFirstName())
                .lastName(studentCreationDto.getLastName())
                .registrationDate(LocalDate.now())
                .phoneNumber(studentCreationDto.getPhoneNumber())
                .studentState(studentCreationDto.getStudentState())
                .appliedOffersId(new HashSet<>())
                .interviewsDate(new HashSet<>())
                .hasValidCv(false)
                .build();
    }

    public static StudentDetailsDto toDto(Student student) {
        return StudentDetailsDto.builder()
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

    public static StudentProfileDto toProfileDto(Student student) {
        return StudentProfileDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .registrationDate(student.getRegistrationDate())
                .phoneNumber(student.getPhoneNumber())
                .studentState(student.getStudentState())
                .nbrOfAppliedOffers(student.getAppliedOffersId().isEmpty() ? student.getAppliedOffersId().size() : 0)
                .nbrOfExclusiveOffers(student.getExclusiveOffersId().isEmpty() ? student.getExclusiveOffersId().size() : 0)
                .hasValidCv(student.getHasValidCv())
                .recentInterviewDate(student.getInterviewsDate().isEmpty() ? null : Collections.max(student.getInterviewsDate()))
                .build();
    }

}
