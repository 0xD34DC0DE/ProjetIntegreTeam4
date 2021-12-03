package com.team4.backend.pdf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class PdfTemplateTest {

    @Mock
    private SpringTemplateEngine springTemplateEngineMock;


    @Test
    void shouldGeneratePdf() {
        //ARRANGE
        SpringTemplateEngine springTemplateEngineMoc = mock(SpringTemplateEngine.class);
        when(springTemplateEngineMock.process(any(String.class), any(IContext.class))).thenReturn("<div></div>");

        PdfTemplate pdfTemplate = new PdfTemplate("a", Map.of("a", "b")) {};

        //ACT && ASSERT
        assertDoesNotThrow(() -> {
            ByteArrayOutputStream byteArrayOutputStream =
                    pdfTemplate.generatePdf(springTemplateEngineMock, "");
        });
    }
}
