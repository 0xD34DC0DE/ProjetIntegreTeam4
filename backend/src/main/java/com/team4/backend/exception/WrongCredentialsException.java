package com.team4.backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class WrongCredentialsException extends Exception {

    public WrongCredentialsException(String message) {
        super(message);
    }

}
