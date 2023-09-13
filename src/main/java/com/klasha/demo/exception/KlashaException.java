package com.klasha.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class KlashaException extends Exception{
    private final HttpStatus status;

    public KlashaException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static KlashaException badRequest(String message) {
        return new KlashaException(message, HttpStatus.BAD_REQUEST);
    }
}