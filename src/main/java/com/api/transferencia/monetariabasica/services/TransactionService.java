package com.api.transferencia.monetariabasica.services;

import com.api.transferencia.monetariabasica.commons.exceptions.CreateTransactionException;
import com.api.transferencia.monetariabasica.dtos.TransactionDTO;
import com.api.transferencia.monetariabasica.models.transaction.Transaction;
import com.api.transferencia.monetariabasica.models.user.User;
import com.api.transferencia.monetariabasica.repositories.TransactionRepository;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final UserService userService;

    private final RestTemplate restTemplate;

    private final Logger logger;
    @Value("${client.authorization-mock.url}")
    private String url;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              UserService userService, RestTemplate restTemplate,
                              Logger logger) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.logger = logger;
    }

    public void createTransaction(TransactionDTO transaction) {
        logger.info("Criando Transaction");
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validatesTransaction(sender, transaction.value());

        boolean authorized = this.authorizedTransaction(sender, transaction.value());
        if (!authorized) {
            logger.warning("Erro ao criar transaction");
            throw new CreateTransactionException("Transação inválida ou não autorizada");
        }
        persistTransaction(sender, transaction, receiver);
    }

    public boolean authorizedTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String message = String.valueOf(response.getBody());
            return "Autorizado".equalsIgnoreCase(message);
        } else
            return false;
    }

    private void persistTransaction(User sender, TransactionDTO transaction,
                                    User receiver) {
        logger.info("Persistindo transaction");
        Transaction newTransaction = new Transaction();
        newTransaction.setSender(sender);
        newTransaction.setValue(transaction.value());
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(sender.getBalance().add(transaction.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
    }

}
