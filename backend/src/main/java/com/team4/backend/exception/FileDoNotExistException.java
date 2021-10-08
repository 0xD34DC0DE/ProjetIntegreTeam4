package com.team4.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileDoNotExistException extends Exception {

    public FileDoNotExistException(String message) {
        super(message);
    }

}
