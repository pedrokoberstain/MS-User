package com.compassuol.challenge3.User.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

    private String firstName;
    private String lastName;
    private String email;
    private String cpf;
    private String birthdate;
    private String cep;
    private boolean active;
}
