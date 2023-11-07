package com.api.transferencia.monetariabasica.services;

import com.api.transferencia.monetariabasica.dtos.TransactionDTO;
import com.api.transferencia.monetariabasica.models.user.User;
import com.api.transferencia.monetariabasica.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final UserService userService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public void createTransaction(TransactionDTO transaction) {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validatesTransaction(sender, transaction.value());

        if ()
    }

    public boolean authorizedTransaction(){

    }

}
