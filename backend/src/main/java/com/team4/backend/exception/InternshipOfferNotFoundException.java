package com.team4.backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InternshipOfferNotFoundException extends Exception {

    public InternshipOfferNotFoundException(String message) {
        super(message);
    }

}
