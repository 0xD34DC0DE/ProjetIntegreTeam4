package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDoNotExistException extends Exception {

    public UserDoNotExistException(String message) {
        super(message);
    }
}
