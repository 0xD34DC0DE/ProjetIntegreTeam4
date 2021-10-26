package com.team4.backend.pdf;

import com.lowagie.text.DocumentException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public abstract class PdfTemplate {

    private final String template_filename;

    private final Map<String, Object> variables;

    protected PdfTemplate(String template_filename, Map<String, Object> variables) {
        this.template_filename = template_filename;
        this.variables = variables;
    }

    public ByteArrayOutputStream generatePdf(SpringTemplateEngine springTemplateEngine) throws DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String html = loadAndFillTemplate(springTemplateEngine, getContext());

        ITextRenderer renderer = new ITextRenderer(20.0f * 4.0f / 3.0f, 20);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        return outputStream;
    }

    private Context getContext() {
        Context context = new Context();
        context.setVariables(variables);
        return context;
    };

    private String loadAndFillTemplate(SpringTemplateEngine templateEngine, Context context) {
        return templateEngine.process(template_filename, context);
    }
}
