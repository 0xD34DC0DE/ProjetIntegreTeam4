package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}