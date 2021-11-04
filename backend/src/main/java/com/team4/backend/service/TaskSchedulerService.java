package com.team4.backend.service;

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
public class TaskSchedulerService {

    private final StudentService studentService;

    public TaskSchedulerService(StudentService studentService) {
        this.studentService = studentService;
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

}
