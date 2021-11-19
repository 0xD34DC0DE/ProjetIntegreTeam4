package com.team4.backend.pdf;

import java.util.Map;

public class EvaluationPdf extends PdfTemplate{

    public EvaluationPdf(Map<String, Object> variables) {
        super("Evaluation", variables);
    }

}
