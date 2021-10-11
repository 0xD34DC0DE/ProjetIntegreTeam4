package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InternshipOfferNotFoundException extends Exception {

    public InternshipOfferNotFoundException(String message) {
        super(message);
    }
}
