package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateEntryException extends Exception{
    public DuplicateEntryException(String message) {
        super(message);
    }
}
