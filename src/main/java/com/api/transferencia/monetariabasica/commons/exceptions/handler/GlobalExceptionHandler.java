package com.api.transferencia.monetariabasica.commons.exceptions.handler;

import com.api.transferencia.monetariabasica.commons.exceptions.NotFoundException;
import com.api.transferencia.monetariabasica.commons.exceptions.ValidateTransactionException;
import jakarta.annotation.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDuplicateDataEntry(
            DataIntegrityViolationException exception) {

        return createProblemDetail(exception, HttpStatus.BAD_REQUEST,
                                   null,
                                   "Duplicate data entry",
                                   "Usuário com documento ou email já cadastrado",
                                   null,
                                   Map.of(TIMESTAMP, LocalDateTime.now())
        );

    }

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail handleNoSuchElement(
            NoSuchElementException exception) {

        return createProblemDetail(exception, HttpStatus.NOT_FOUND,
                                   null,
                                   "No such element",
                                   "Usuário não encontrado",
                                   null,
                                   Map.of(TIMESTAMP, LocalDateTime.now()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException exception) {
        return createProblemDetail(exception, HttpStatus.NOT_FOUND,
                                   null,
                                   "Not found",
                                   "Nenhum usuário cadastrado",
                                   null,
                                   Map.of(TIMESTAMP, LocalDateTime.now()));
    }

    @ExceptionHandler(ValidateTransactionException.class)
    public ProblemDetail handleValidateTransaction(
            ValidateTransactionException exception) {

        return createProblemDetail(exception, HttpStatus.BAD_REQUEST,
                                   null,
                                   "Transação inválida",
                                   exception.getMessage(),
                                   null,
                                   Map.of(TIMESTAMP, LocalDateTime.now()));
    }

    private ProblemDetail createProblemDetail(Throwable exception, HttpStatus status,
                                              @Nullable URI type,
                                              @Nullable String title,
                                              @Nullable String detail,
                                              @Nullable URI instance,
                                              @Nullable Map<String, Object> properties) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        if (title != null)
            problemDetail.setTitle(title);
        if (detail != null)
            problemDetail.setDetail(detail);
        if (type != null)
            problemDetail.setType(type);
        if (instance != null)
            problemDetail.setInstance(instance);
        if (properties != null && !properties.isEmpty()) {
            properties.forEach(problemDetail::setProperty);
        }
        return problemDetail;
    }
}
