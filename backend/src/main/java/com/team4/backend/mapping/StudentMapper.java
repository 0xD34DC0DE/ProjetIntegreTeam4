package com.team4.backend.mapping;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.dto.StudentProfileDto;
import com.team4.backend.model.Student;
import com.team4.backend.util.DateUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

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
                .evaluationsDates(new TreeSet<>())
                .interviewsDate(new TreeSet<>())
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
                .nbrOfAppliedOffers(student.getAppliedOffersId().size())
                .nbrOfExclusiveOffers(student.getExclusiveOffersId().size())
                .nbrOfInterviews(student.getInterviewsDate().size())
                .hasValidCv(student.getHasValidCv())
                .closestInterviewDate(DateUtil.retrieveDateClosestToToday(student.getInterviewsDate()))
                .build();
    }

}
