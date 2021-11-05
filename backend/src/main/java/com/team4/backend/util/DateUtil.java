package com.team4.backend.util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DateUtil {

    public static LocalDate retrieveDateClosestToToday(Set<LocalDate> localDateSet) {
        LocalDate currentDate = LocalDate.now();
        Set<LocalDate> dateComingBeforeToday = localDateSet
                .stream()
                .filter(date -> currentDate.isAfter(date) || currentDate.isEqual(date))
                .collect(Collectors.toSet());
        Set<LocalDate> dateComingAfterToday = localDateSet
                .stream()
                .filter(date -> currentDate.isBefore(date) || currentDate.isEqual(date))
                .collect(Collectors.toSet());

        return dateComingAfterToday.isEmpty() ?
                (dateComingBeforeToday.isEmpty() ? null : Collections.max(dateComingBeforeToday)) :
                Collections.min(dateComingAfterToday);
    }

}
