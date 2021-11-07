package com.team4.backend.testdata;


import com.team4.backend.model.Semester;
import com.team4.backend.model.enums.SemesterName;

import java.time.LocalDateTime;
import java.time.Month;

public abstract class SemesterMockData {

    public static Semester getSemester() {
        return Semester.builder()
                .fullName(SemesterName.AUTUMN + " 2021")
                .from(LocalDateTime.now()
                        .withMonth(Month.AUGUST.getValue())
                        .withYear(2021)
                        .withDayOfMonth(20)
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0)
                )
                .to(LocalDateTime.now()
                        .withMonth(Month.DECEMBER.getValue())
                        .withYear(2021)
                        .withDayOfMonth(21)
                        .withHour(0)
                        .withMinute(0)
                        .withSecond(0))
                .build();
    }
}
