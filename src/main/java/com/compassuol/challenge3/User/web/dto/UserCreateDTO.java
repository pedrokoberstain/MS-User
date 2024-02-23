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

    private Long id;
    private boolean active;

    private String firstName;
    private String lastName;
    private String cpf;
    private String birthdate;
    @NotBlank
    @Email(message = "Invalid email address", regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    private String cep;
    @NotBlank(message = "The password must be at least 6 characters long")
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public Object getUsername() {
        return null;
    }
}
