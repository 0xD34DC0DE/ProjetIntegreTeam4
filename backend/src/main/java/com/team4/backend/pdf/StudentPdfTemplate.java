package com.team4.backend.pdf;

import java.util.Map;

public class StudentPdfTemplate extends PdfTemplate {

    public StudentPdfTemplate(Map<String, Object> variables) {
        super("pdf_test", variables);
    }

}
