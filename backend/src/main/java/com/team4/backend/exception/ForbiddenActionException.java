package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ForbiddenActionException extends Exception {

    public ForbiddenActionException(String message) {
        super(message);
    }
}
