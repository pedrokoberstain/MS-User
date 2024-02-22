package com.compassuol.challenge3.User.data.vo.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String birthdate;
    private String email;
    private String cep;
    private String password;
    private boolean active;

}
