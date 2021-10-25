package com.team4.backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenActionException extends Exception {

    public ForbiddenActionException(String message) {
        super(message);
    }

}
