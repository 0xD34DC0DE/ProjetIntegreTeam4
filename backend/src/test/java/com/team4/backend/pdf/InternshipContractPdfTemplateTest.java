package com.team4.backend.pdf;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class InternshipContractPdfTemplateTest {

    @Test
    void shouldConstructInternshipContractPdf() {
        //ARRANGE
        Map<String, Object> values = Map.of("test", 1);

        //ACT && ASSERT
        assertDoesNotThrow(() -> {
            InternshipContractPdfTemplate internshipContractPdfTemplate = new InternshipContractPdfTemplate(values);
        });
    }
}
