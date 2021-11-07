package com.team4.backend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@Data
public class SemesterDto implements Serializable {
    String currentSemesterFullName;
    Set<String> semestersFullNames;

    public SemesterDto() {
        this.semestersFullNames = new TreeSet<>();
    }

}
