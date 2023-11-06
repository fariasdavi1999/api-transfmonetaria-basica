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

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public void validatesTransaction(User sender, BigDecimal value) {
        if (sender.getUserType() == UserType.TRADER) {
            throw new ValidateTransactionException(
                    "Usuário não pode realizar transações");
        }
        if (sender.getBalance().compareTo(value) < 0) {
            throw new ValidateTransactionException("Usuário não tem saldo");
        }
    }

    public User findUserById(UUID id, Throwable error) {
        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Usuário não encontrado", error.getCause()));
    }

}
