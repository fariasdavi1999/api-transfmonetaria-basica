package com.api.transferencia.monetariabasica.commons.exceptions;

public class ValidateTransactionException extends RuntimeException {
    public ValidateTransactionException(String message) {
        super(message);
    }

    public ValidateTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
