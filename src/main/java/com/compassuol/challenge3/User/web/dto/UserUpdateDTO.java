package com.compassuol.challenge3.User.web.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO implements Serializable {
    private String firstName;
    private String lastName;
    private String cpf;
    private Date birthdate;
    @Email(message = "Invalid email address", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    private String cep;
    private String password;

    public String getPassword() {
        return this.password;
    }
}
