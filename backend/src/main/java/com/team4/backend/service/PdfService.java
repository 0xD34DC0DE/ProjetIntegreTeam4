package com.team4.backend.service;

import com.lowagie.text.DocumentException;
import com.team4.backend.exception.PdfGenerationErrorException;
import com.team4.backend.pdf.PdfTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    private final String PDF_CSS_DIR = "/static/";

    private final String cssResourcesAbsolutePath;

    @Qualifier("thymeleafTemplateEngine")
    private final SpringTemplateEngine templateEngine;

    private final ResourceLoader resourceLoader;

    public PdfService(SpringTemplateEngine templateEngine, ResourceLoader resourceLoader) throws IOException {
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
        this.cssResourcesAbsolutePath = new ClassPathResource(PDF_CSS_DIR).getURL().toExternalForm();
    }

    public Mono<byte[]> renderPdf(PdfTemplate pdfTemplate) {
        try {
            ByteArrayOutputStream outputStream = pdfTemplate.generatePdf(templateEngine, cssResourcesAbsolutePath);
            return Mono.just(outputStream.toByteArray());
        } catch (DocumentException e) {
            return Mono.error(new PdfGenerationErrorException());
        }
    }
}
