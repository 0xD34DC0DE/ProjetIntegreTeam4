package com.team4.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidPageRequestException extends Exception {
    public InvalidPageRequestException(String message) {
        super(message);
    }

    public InvalidPageRequestException() {}
}
