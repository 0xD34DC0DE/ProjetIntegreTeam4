package com.team4.backend.testdata;


import com.team4.backend.dto.SemesterDto;
import com.team4.backend.model.Semester;
import com.team4.backend.model.enums.SemesterName;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class SemesterMockData {

    public static List<Semester> getListSemester() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return Arrays.asList(
                Semester.builder()
                        .fullName(SemesterName.FALL + " " + currentDateTime.getYear())
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
                        .fullName(SemesterName.WINTER + " " + currentDateTime.plusYears(1).getYear())
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
                        .fullName(SemesterName.SUMMER + " " + currentDateTime.plusYears(1).getYear())
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

    public static SemesterDto getSemesterDto() {
        return SemesterDto.builder()
                .currentSemesterFullName(SemesterName.FALL + " " + LocalDateTime.now().getYear())
                .semestersFullNames(new TreeSet<>(getListSemester().stream().map(Semester::getFullName).collect(Collectors.toList())))
                .build();
    }

}
