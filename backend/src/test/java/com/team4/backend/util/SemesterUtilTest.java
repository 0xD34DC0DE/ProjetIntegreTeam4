package com.team4.backend.util;

import com.team4.backend.model.Semester;
import com.team4.backend.testdata.SemesterMockData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SemesterUtilTest {

    @Test
    void shouldCheckIfDatesAreInsideRangeOfCurrentSemesterAndThenReturnTrue() {
        //ACT
        Semester semester = SemesterMockData.getListSemester().get(0);

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime date1 = LocalDateTime.now()
                .withMonth(Month.OCTOBER.getValue())
                .withYear(currentDate.getYear())
                .withDayOfMonth(2);
        LocalDateTime date2 = LocalDateTime.now()
                .withMonth(Month.OCTOBER.getValue())
                .withYear(currentDate.getYear())
                .withDayOfMonth(25);


        //ARRANGE
        Boolean isInsideRange = SemesterUtil.checkIfDatesAreInsideRangeOfSemester(semester, date1, date2);

        //ASSERT
        assertTrue(isInsideRange);
    }

    @Test
    void shouldCheckIfDatesAreInsideRangeOfCurrentSemesterAndThenReturnFalse() {
        //ACT
        Semester semester = SemesterMockData.getListSemester().get(0);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime date1 = LocalDateTime.now()
                .withMonth(Month.OCTOBER.getValue())
                .withYear(currentDate.getYear())
                .withDayOfMonth(2);
        LocalDateTime date2 = LocalDateTime.now()
                .withMonth(Month.JANUARY.getValue())
                .withYear(currentDate.getYear() + 1)
                .withDayOfMonth(25);


        //ARRANGE
        Boolean isInsideRange = SemesterUtil.checkIfDatesAreInsideRangeOfSemester(semester, date1, date2);

        //ASSERT
        assertFalse(isInsideRange);
    }

}
