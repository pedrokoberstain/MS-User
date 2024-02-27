package com.compassuol.challenge3.User.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private LocalDate birthdate;
    private String email;
    private String cep;
    private boolean active;
    private String role;

}
