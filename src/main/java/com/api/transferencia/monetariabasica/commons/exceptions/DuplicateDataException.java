package com.api.transferencia.monetariabasica.commons.exceptions;

import org.springframework.http.HttpStatus;

public record DuplicateDataException(String message, Integer statusCode) {

}
