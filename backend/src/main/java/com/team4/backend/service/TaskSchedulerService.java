package com.team4.backend.service;

import com.team4.backend.meta.ExcludeFromGeneratedCoverage;
import com.team4.backend.util.SemesterUtil;
import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log
@Order(3)
@Service
@EnableScheduling
@ExcludeFromGeneratedCoverage
public class TaskSchedulerService {

    private final StudentService studentService;

    private final SemesterService semesterService;

    public TaskSchedulerService(StudentService studentService, SemesterService semesterService) {
        this.studentService = studentService;
        this.semesterService = semesterService;
    }

    @Scheduled(cron = "0 00 0 ? * *")
    private void updateStudentStateForAllStudentThatInterviewDateHasPassed() {
        studentService.updateStudentStateForAllStudentThatInterviewDateHasPassed()
                .subscribe(nbrOfUpdateStudent -> log.info(
                        "NBR OF STUDENT THAT HAVE THEIR STATE UPDATED : " +
                                nbrOfUpdateStudent +
                                ", time : " +
                                LocalDateTime.now()));
    }

    @Scheduled(cron = "0 00 1 1 8 ?")
    public void initializeSemestersAnnually() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        log.info("*************************************************");
        log.info("NEW SCHOOL YEAR : " + currentDateTime.getYear());
        log.info("*************************************************");

        semesterService.initializeSemestersAnnually(SemesterUtil.getSemesters(currentDateTime))
                .subscribe(semester -> log.info("NEW SEMESTER CREATED : " + semester.toString()));
    }

}
