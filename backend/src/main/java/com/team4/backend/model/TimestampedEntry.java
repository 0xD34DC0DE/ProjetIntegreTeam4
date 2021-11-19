package com.team4.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimestampedEntry implements Serializable, Comparable<TimestampedEntry> {

    private String email;

    private LocalDateTime date;

    @Override
    public int compareTo(TimestampedEntry o) {
        if (this.getDate().isBefore(o.getDate()))
            return -1;
        if (this.getDate().isAfter(o.getDate()))
            return 1;
        return 0;
    }
}
