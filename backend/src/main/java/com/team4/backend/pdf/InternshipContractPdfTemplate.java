package com.team4.backend.pdf;


import java.util.Map;

public class InternshipContractPdfTemplate extends PdfTemplate {

    public InternshipContractPdfTemplate(Map<String, Object> variables) {
        super("internship_contract", variables);
    }

}
