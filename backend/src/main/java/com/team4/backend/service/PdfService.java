package com.team4.backend.service;

import com.lowagie.text.DocumentException;
import com.team4.backend.exception.PdfGenerationErrorException;
import com.team4.backend.pdf.PdfTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    private final String PDF_CSS_DIR = "/static/";

    private final String cssResourcesAbsolutePath;

    private final SpringTemplateEngine templateEngine;

    public PdfService(SpringTemplateEngine templateEngine) throws IOException {
        this.templateEngine = templateEngine;
        this.cssResourcesAbsolutePath = new ClassPathResource(PDF_CSS_DIR).getURL().toExternalForm();
    }

    /**
     * Render the pdf to a byte object using the given template object
     * @param pdfTemplate Object of a class that extends pdfTemplate
     * @return Byte array of the generated pdf
     */
    public Mono<byte[]> renderPdf(PdfTemplate pdfTemplate) {
        try {
            ByteArrayOutputStream outputStream = pdfTemplate.generatePdf(templateEngine, cssResourcesAbsolutePath);
            return Mono.just(outputStream.toByteArray());
        } catch (DocumentException e) {
            return Mono.error(new PdfGenerationErrorException());
        }
    }

}
