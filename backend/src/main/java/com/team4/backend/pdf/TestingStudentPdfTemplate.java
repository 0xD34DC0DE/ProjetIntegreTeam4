package com.team4.backend.pdf;

import java.util.Map;

public class TestingStudentPdfTemplate extends PdfTemplate {

    public TestingStudentPdfTemplate(Map<String, Object> variables) {
        super("pdf_test", variables);
    }

}
