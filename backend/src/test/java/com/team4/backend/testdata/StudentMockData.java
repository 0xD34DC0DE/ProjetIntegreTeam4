package com.team4.backend.testdata;

import com.team4.backend.dto.StudentDetailsDto;
import com.team4.backend.model.Student;
import com.team4.backend.model.enums.StudentState;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.*;

public abstract class StudentMockData {

    public static Student getMockStudent() {
        return Student.studentBuilder()
                .id("mock_id")
                .email("123456789@gmail.com")
                .password("passwd")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123-456-7890")
                .studentState(StudentState.REGISTERED)
                .hasValidCv(false)
                .registrationDate(null) // Autogenerated (creation time) when null
                .exclusiveOffersId(getListExclusiveOfferIds())
                .interviewsDate(new TreeSet<>(Arrays.asList(
                        LocalDate.now().minusWeeks(3),
                        LocalDate.now(),
                        LocalDate.now().minusDays(5))))
                .appliedOffersId(new HashSet<>())
                .build();
    }

    public static StudentDetailsDto getMockStudentDto() {
        return StudentDetailsDto.builder()
                .id("mock_id")
                .email("123456789@gmail.com")
                .password("passwd")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123-456-7890")
                .studentState(StudentState.REGISTERED)
                .hasValidCv(false)
                .registrationDate(null) // Autogenerated (creation time) when null
                .build();
    }

    public static Flux<Student> getAllStudents() {
        return Flux.just(
                Student.studentBuilder().id("1234567890fgh").email("student@gmail.com").password("mdp")
                        .firstName("Mario").lastName("Bros").phoneNumber("514-123-1234")
                        .studentState(StudentState.REGISTERED).hasValidCv(false).registrationDate(null) // Autogenerated (creation time) when null
                        .build(),
                Student.studentBuilder().id("000111222abc").email("eleve@gmail.com").password("mdpEleve")
                        .firstName("Luigi").lastName("Bros").phoneNumber("514-444-1235")
                        .studentState(StudentState.REGISTERED).hasValidCv(false).registrationDate(null) // Autogenerated (creation time) when null
                        .build());
    }

    public static List<Student> getAllStudentsToUpdate() {
        return Arrays.asList(
                Student.studentBuilder()
                        .firstName("test")
                        .lastName("test")
                        .email("testing_1@gmail.com")
                        .password("password1")
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .interviewsDate(new TreeSet<>(Arrays.asList(
                                LocalDate.now().minusWeeks(3),
                                LocalDate.now(),
                                LocalDate.now().minusDays(5))))
                        .build(),
                Student.studentBuilder()
                        .email("testing_2@gmail.com")
                        .firstName("test")
                        .lastName("test")
                        .studentState(StudentState.INTERNSHIP_NOT_FOUND)
                        .password("password2")
                        .interviewsDate(new TreeSet<>())
                        .build()
        );
    }

    public static List<Student> getListStudent(int count) {
        List<Student> students = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Student student = Student.studentBuilder()
                    .id("id_" + i)
                    .build();

            students.add(student);
        }

        return students;
    }

    public static Set<String> getListExclusiveOfferIds() {
        Set<String> exclusiveOffers = new HashSet<>();
        exclusiveOffers.add("exclusive_id_1");
        exclusiveOffers.add("exclusive_id_2");
        return exclusiveOffers;
    }

    public static Flux<Student> getAssignedStudents() {
        return Flux.just(Student.studentBuilder().email("12395432@gmail.com").build(),
                Student.studentBuilder().email("toto23@outlook.com").build());
    }

}
