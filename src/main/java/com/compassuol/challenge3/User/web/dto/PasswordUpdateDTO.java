package com.compassuol.challenge3.User.web.dto;

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
public class PasswordUpdateDTO implements Serializable {
    @NotBlank(message = "The password must be at least 6 characters long")
    private String password;
}