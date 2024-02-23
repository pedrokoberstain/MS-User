package com.compassuol.challenge3.User.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO implements Serializable {

    @NotBlank(message = "The first name must be at least 3 characters long")
    private String firstName;
    @NotBlank(message = "The last name must be at least 3 characters long")
    private String lastName;
    @NotBlank
    private String cpf;
    private String birthdate;
    @NotBlank
    @Email(message = "Invalid email address", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    private String cep;
    @NotBlank(message = "The password must be at least 6 characters long")
    private String password;
}