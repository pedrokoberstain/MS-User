package com.compassuol.challenge3.User.web.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO implements Serializable {
    private String firstName;
    private String lastName;
    private String cpf;
    private String birthdate;
    @Email(message = "Invalid email address", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    private String cep;

    public String getPassword() {
        return this.getPassword();
    }
}
