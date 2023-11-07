package com.api.transferencia.monetariabasica.commons.exceptions;

public class CreateTransactionException extends RuntimeException {
    public CreateTransactionException(String message) {
        super(message);
    }

    public CreateTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
