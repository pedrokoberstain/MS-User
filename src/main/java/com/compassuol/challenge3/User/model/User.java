package com.compassuol.challenge3.User.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

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

    @NotNull(message = "The birthdate must be provided")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

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

    @AssertTrue(message = "The user must be active or inactive")
    private boolean isActiveValid() {
        return active == true || active == false;
    }

    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        else return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}