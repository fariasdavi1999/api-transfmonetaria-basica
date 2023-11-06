package com.api.transferencia.monetariabasica.repositories;

import com.api.transferencia.monetariabasica.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByDocument(String document);

    Optional<User> findUserById(UUID id);
}
