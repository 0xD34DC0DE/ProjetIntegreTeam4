package com.team4.backend.pdf;

import java.util.Map;

public class NonValidatedOffersPdfTemplate extends PdfTemplate{
    public NonValidatedOffersPdfTemplate(Map<String, Object> variables) {
        super("NonValidatedOffers", variables);
    }
}
