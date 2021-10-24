package com.team4.backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateEntryException extends Exception{
    public DuplicateEntryException(String message) {
        super(message);
    }
}
