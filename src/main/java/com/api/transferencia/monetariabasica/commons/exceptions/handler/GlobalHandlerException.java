package com.api.transferencia.monetariabasica.commons.exceptions.handler;

import com.api.transferencia.monetariabasica.commons.exceptions.DuplicateDataException;
import com.api.transferencia.monetariabasica.commons.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException extends RuntimeException {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DuplicateDataException> handleDuplicateDataEntry(
            DataIntegrityViolationException exception) {

        DuplicateDataException dataException =
                new DuplicateDataException("Usuário já cadastrado",
                                           400);
        return ResponseEntity.badRequest().body(dataException);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<NotFoundException> handleEntityNotFound(
            EntityNotFoundException exception) {

        NotFoundException notFoundException =
                new NotFoundException("Usuário não encontrado", 404);

        return ResponseEntity.badRequest().body(notFoundException);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<NotFoundException> handleEmptyResult() {

        NotFoundException notFoundException =
                new NotFoundException("Nenhum usuário cadastrado", 404);

        return ResponseEntity.badRequest().body(notFoundException);
    }
}
