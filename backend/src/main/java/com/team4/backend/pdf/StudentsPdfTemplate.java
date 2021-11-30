package com.team4.backend.pdf;

import java.util.Map;

public class StudentsPdfTemplate extends PdfTemplate{

    public StudentsPdfTemplate(Map<String, Object> variables) {
        super("Students", variables);
    }

}

