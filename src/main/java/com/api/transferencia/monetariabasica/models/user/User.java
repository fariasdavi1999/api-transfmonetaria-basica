package com.api.transferencia.monetariabasica.models.user;

import com.api.transferencia.monetariabasica.dtos.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    @NotBlank
    @Column(unique = true)
    private String document;

    @NotBlank
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO data) {
        BeanUtils.copyProperties(data, this);
    }
}
