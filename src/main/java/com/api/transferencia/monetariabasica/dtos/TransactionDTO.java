package com.api.transferencia.monetariabasica.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(UUID senderId, BigDecimal value, UUID receiverId) {

}
