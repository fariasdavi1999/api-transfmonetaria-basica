package com.api.transferencia.monetariabasica.dtos;

import com.api.transferencia.monetariabasica.models.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, String email,
                      String password,
                      BigDecimal balance,
                      UserType userType) {
}
