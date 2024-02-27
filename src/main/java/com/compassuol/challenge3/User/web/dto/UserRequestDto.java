package com.compassuol.challenge3.User.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    @NotBlank(message = "O primeiro nome é obrigatório e deve ter no mínimo 3 caracteres")
    @Size(min = 3, message = "O primeiro nome deve ter no mínimo 3 caracteres")
    private String firstName;

    @NotBlank(message = "O sobrenome é obrigatório e deve ter no mínimo 3 caracteres")
    @Size(min = 3, message = "O sobrenome deve ter no mínimo 3 caracteres")
    private String lastName;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    private String cpf;

    @NotBlank(message = "A data de nascimento é obrigatória")
    private String birthdate;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "O CEP é obrigatório")
    private String cep;

    @NotBlank(message = "A senha é obrigatória e deve ter no mínimo 6 caracteres")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    private boolean active;
}
