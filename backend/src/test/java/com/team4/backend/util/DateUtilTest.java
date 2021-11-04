package com.team4.backend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    @Test
    void shouldRetrieveDateClosestToTodayWhenItIsAfterToday() {
        //ARRANGE
        Set<LocalDate> dates = new TreeSet<>(
                Arrays.asList(

                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusMonths(1),
                        LocalDate.now().plusWeeks(1),
                        LocalDate.now().minusDays(5)
                )
        );

        //ACT
        LocalDate closestDate = DateUtil.retrieveDateClosestToToday(dates);

        //ASSERT
        assertEquals(LocalDate.now().plusDays(1), closestDate);
    }

    @Test
    void shouldRetrieveDateClosestToTodayWhenItIsBeforeToday() {
        //ARRANGE
        Set<LocalDate> dates = new TreeSet<>(
                Arrays.asList(
                        LocalDate.now().minusMonths(1),
                        LocalDate.now().minusWeeks(1),
                        LocalDate.now().minusDays(5)
                )
        );

        //ACT
        LocalDate closestDate = DateUtil.retrieveDateClosestToToday(dates);

        //ASSERT
        assertEquals(LocalDate.now().minusDays(5), closestDate);
    }


    @Test
    void shouldNotReturnDateClosesToToday() {
        //ARRANGE
        Set<LocalDate> dates = new TreeSet<>();

        //ACT
        LocalDate closestDate = DateUtil.retrieveDateClosestToToday(dates);

        //ASSERT
        assertNull(closestDate);
    }

}
