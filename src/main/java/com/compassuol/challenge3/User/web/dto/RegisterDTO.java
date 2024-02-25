package com.compassuol.challenge3.User.web.dto;

import com.compassuol.challenge3.User.model.UserRole;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public record RegisterDTO(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @CPF(message = "Invalid CPF") String cpf,
        @NotNull(message = "Birthdate must be provided") Date birthdate,
        @Email(message = "Invalid email address") String email,
        @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Invalid CEP") String cep,
        @NotBlank(message = "Password is required") String password,
        @NotNull(message = "Role is required") UserRole role
) {}
