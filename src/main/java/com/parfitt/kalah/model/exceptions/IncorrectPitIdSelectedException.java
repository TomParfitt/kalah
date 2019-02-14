package com.parfitt.kalah.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectPitIdSelectedException extends RuntimeException {

    public IncorrectPitIdSelectedException(String message) {
        super(message);
    }
}
