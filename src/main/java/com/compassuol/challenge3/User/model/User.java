package com.compassuol.challenge3.User.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 80, message = "The first name must be at least 3 characters long")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 80, message = "The last name must be at least 3 characters long")
    private String lastName;

    @CPF(message = "Invalid CPF")
    @Column(unique = true, nullable = false)
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthdate;

    @Email(message = "Invalid email address")
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Invalid CEP")
    private String cep;

    @Column(nullable = false)
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;

    @NotNull(message = "The user must be active or inactive")
    private boolean active;

}
