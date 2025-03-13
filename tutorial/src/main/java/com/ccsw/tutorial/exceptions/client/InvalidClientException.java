package com.ccsw.tutorial.exceptions.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidClientException extends RuntimeException {
    public InvalidClientException(String message) {
        super(message);
    }
}