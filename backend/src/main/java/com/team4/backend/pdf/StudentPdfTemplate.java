package com.team4.backend.pdf;

import com.team4.backend.model.Student;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;

public class StudentPdfTemplate extends PdfTemplate {

    public StudentPdfTemplate(Map<String, Object> variables) {
        super("pdf_test", variables);
    }

}
