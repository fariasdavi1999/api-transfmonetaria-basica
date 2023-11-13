package com.api.transferencia.monetariabasica.commons.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message, Integer statusCode) {
        // TODO document why this constructor is empty
    }
}
