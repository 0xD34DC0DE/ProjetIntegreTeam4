package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidPageRequestException extends Exception {
    public InvalidPageRequestException(String message) {
        super(message);
    }

}
