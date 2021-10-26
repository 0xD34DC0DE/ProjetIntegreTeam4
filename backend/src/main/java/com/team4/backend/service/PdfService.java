package com.team4.backend.service;

import com.lowagie.text.DocumentException;
import com.team4.backend.exception.PdfGenerationErrorException;
import com.team4.backend.pdf.PdfTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    @Qualifier("thymeleafTemplateEngine")
    private final SpringTemplateEngine templateEngine;

    public PdfService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public Mono<byte[]> renderPdf(PdfTemplate pdfTemplate) {
        try {
            ByteArrayOutputStream outputStream = pdfTemplate.generatePdf(templateEngine);
            return Mono.just(outputStream.toByteArray());
        } catch (DocumentException e) {
            return Mono.error(new PdfGenerationErrorException());
        }
    }
}
