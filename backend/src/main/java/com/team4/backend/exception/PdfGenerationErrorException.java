package com.team4.backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@NoArgsConstructor
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class PdfGenerationErrorException extends Exception {
    public PdfGenerationErrorException(String message) {
        super(message);
    }
}
