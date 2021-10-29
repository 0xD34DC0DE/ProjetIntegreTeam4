package com.team4.backend.pdf;

import java.util.Map;

public class StudentsPdf extends PdfTemplate{
    public StudentsPdf(Map<String, Object> variables) {
        super("Students", variables);
    }
}

