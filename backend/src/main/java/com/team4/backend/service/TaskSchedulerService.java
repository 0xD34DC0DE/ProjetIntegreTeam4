package com.team4.backend.service;

import com.team4.backend.model.Semester;
import com.team4.backend.model.enums.SemesterName;
import lombok.extern.java.Log;
import org.springframework.cglib.core.Local;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@Log
@Order(3)
@Service
@EnableScheduling
public class TaskSchedulerService {

    private final StudentService studentService;

    private final SemesterService semesterService;

    public TaskSchedulerService(StudentService studentService, SemesterService semesterService) {
        this.studentService = studentService;
        this.semesterService = semesterService;
    }

    @Scheduled(cron = "0 00 0 ? * *")
    private void updateStudentStateForAllStudentThatInterviewDateHasPassed() {
        studentService.updateStudentStateForAllStudentThatInterviewDateHasPassedWeb()
                .subscribe(nbrOfUpdateStudent -> log.info(
                        "NBR OF STUDENT THAT HAVE THEIR STATE UPDATED : " +
                                nbrOfUpdateStudent +
                                ", time : " +
                                LocalDateTime.now()));
    }

    @Scheduled(cron = "0 00 0 ? * *")
    private void initializeYearlySemester(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Semester> semesters = Arrays.asList(
                Semester.builder()
                        .fullName(SemesterName.AUTUMN + " " + currentDateTime.getYear())
                        .from(currentDateTime
                                .withMonth(Month.AUGUST.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(20)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0)
                        )
                        .to(currentDateTime
                                .withMonth(Month.DECEMBER.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(21)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0))
                        .build(),
                Semester.builder()
                        .fullName(SemesterName.WINTER + " " + currentDateTime.getYear())
                        .from(currentDateTime
                                .withMonth(Month.JANUARY.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(20)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0)
                        )
                        .to(currentDateTime
                                .withMonth(Month.MAY.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(30)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0))
                        .build(),
                Semester.builder()
                        .fullName(SemesterName.WINTER + " " + currentDateTime.getYear())
                        .from(currentDateTime
                                .withMonth(Month.JANUARY.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(20)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0)
                        )
                        .to(currentDateTime
                                .withMonth(Month.MAY.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(30)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0))
                        .build()
        );

    }

}
