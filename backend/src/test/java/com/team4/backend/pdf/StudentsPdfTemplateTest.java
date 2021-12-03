package com.team4.backend.pdf;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StudentsPdfTemplateTest {

    @Test
    void shouldConstructStudentsTemplate() {
        //ARRANGE
        Map<String, Object> values = Map.of("test", 1);

        //ACT && ASSERT
        assertDoesNotThrow(() -> {
            StudentsPdfTemplate testingStudentPdfTemplate = new StudentsPdfTemplate(values);
        });
    }
}
