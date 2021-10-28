package com.team4.backend.util;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DateUtil {

    public static LocalDate retrieveDateClosestToToday(Set<LocalDate> localDateSet) {
        List<LocalDate> dateComingAfterToday = localDateSet
                .stream()
                .filter(date -> LocalDate.now().isBefore(date))
                .collect(Collectors.toList());

        List<LocalDate> dateComingBeforeToday = localDateSet
                .stream()
                .filter(date -> LocalDate.now().isAfter(date))
                .collect(Collectors.toList());

        return dateComingAfterToday.isEmpty() ? Collections.max(dateComingBeforeToday) : Collections.min(dateComingAfterToday);
    }
}
