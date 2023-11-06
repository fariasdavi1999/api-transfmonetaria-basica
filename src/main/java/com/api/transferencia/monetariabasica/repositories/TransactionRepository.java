package com.api.transferencia.monetariabasica.repositories;

import com.api.transferencia.monetariabasica.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
