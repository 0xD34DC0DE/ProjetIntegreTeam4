package com.team4.backend.pdf;

import java.util.Map;

public class OffersPdf extends PdfTemplate{
    public OffersPdf(Map<String, Object> variables) {
        super("Offers", variables);
    }
}
