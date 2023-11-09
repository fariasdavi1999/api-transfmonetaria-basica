package com.api.transferencia.monetariabasica.controllers;

import com.api.transferencia.monetariabasica.dtos.TransactionDTO;
import com.api.transferencia.monetariabasica.models.transaction.Transaction;
import com.api.transferencia.monetariabasica.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transaction) {
        this.transactionService = transaction;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody
                                                         TransactionDTO transaction) {
        Transaction newTransaction =
                this.transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

}
