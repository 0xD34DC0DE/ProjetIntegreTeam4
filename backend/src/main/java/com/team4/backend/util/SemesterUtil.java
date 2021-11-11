package com.team4.backend.util;

import com.team4.backend.model.Semester;
import com.team4.backend.model.enums.SemesterName;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public abstract class SemesterUtil {

    public static Boolean checkIfDatesAreInsideRangeOfSemester(Semester semester,
                                                        LocalDateTime date1,
                                                        LocalDateTime date2) {
        return (semester.getFrom().isEqual(date1) || semester.getFrom().isBefore(date1)) &&
                (semester.getTo().isEqual(date2) || semester.getTo().isAfter(date2));
    }

    public static List<Semester> getSemesters(LocalDateTime currentDateTime) {

        return Arrays.asList(
                Semester.builder()
                        .fullName(SemesterName.FALL + "-" + currentDateTime.getYear())
                        .from(currentDateTime
                                .withMonth(Month.AUGUST.getValue())
                                .withYear(currentDateTime.getYear())
                                .withDayOfMonth(21)
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
                        .fullName(SemesterName.WINTER + "-" + currentDateTime.plusYears(1).getYear())
                        .from(currentDateTime
                                .withMonth(Month.JANUARY.getValue())
                                .withYear(currentDateTime.plusYears(1).getYear())
                                .withDayOfMonth(20)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0)
                        )
                        .to(currentDateTime
                                .withMonth(Month.MAY.getValue())
                                .withYear(currentDateTime.plusYears(1).getYear())
                                .withDayOfMonth(30)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0))
                        .build(),
                Semester.builder()
                        .fullName(SemesterName.SUMMER + "-" + currentDateTime.plusYears(1).getYear())
                        .from(currentDateTime
                                .withMonth(Month.JUNE.getValue())
                                .withYear(currentDateTime.plusYears(1).getYear())
                                .withDayOfMonth(1)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0)
                        )
                        .to(currentDateTime
                                .withMonth(Month.AUGUST.getValue())
                                .withYear(currentDateTime.plusYears(1).getYear())
                                .withDayOfMonth(20)
                                .withHour(0)
                                .withMinute(0)
                                .withSecond(0))
                        .build()
        );
    }

}
