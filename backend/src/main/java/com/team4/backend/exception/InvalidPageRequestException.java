package com.team4.backend.exception;

public class InvalidPageRequestException extends Exception {
    public InvalidPageRequestException(String message) {
        super(message);
    }

    public InvalidPageRequestException() {}
}
