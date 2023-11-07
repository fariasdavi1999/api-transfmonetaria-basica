package com.api.transferencia.monetariabasica.services;

import com.api.transferencia.monetariabasica.commons.exceptions.NotFoundException;
import com.api.transferencia.monetariabasica.commons.exceptions.ValidateTransactionException;
import com.api.transferencia.monetariabasica.models.user.User;
import com.api.transferencia.monetariabasica.models.user.UserType;
import com.api.transferencia.monetariabasica.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final Logger logger;

    @Autowired
    public UserService(UserRepository userRepository,
                       Logger logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
        logger.info("Usuario salvo");
    }

    public void validatesTransaction(User sender, BigDecimal value) {
        logger.info("Validando transaction");
        if (sender.getUserType() == UserType.TRADER) {
            throw new ValidateTransactionException(
                    "Usuário não pode realizar transações");
        }
        if (sender.getBalance().compareTo(value) < 0) {
            throw new ValidateTransactionException("Usuário não tem saldo");
        }
    }

    public User findUserById(UUID id) {
        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Usuário não encontrado"));
    }

}
