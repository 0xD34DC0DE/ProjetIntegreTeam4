package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrongCredentialsException extends Exception {

    public WrongCredentialsException(String message) {
        super(message);
    }

}
