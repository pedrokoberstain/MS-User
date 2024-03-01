package com.compassuol.challenge3.User.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "User", description = "Endpoints relacionados aos usuários")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do usuário gerado automaticamente")
    private Long id;

    @NotBlank(message = "O primeiro nome é obrigatório e deve ter no mínimo 3 caracteres")
    @Schema(description = "Primeiro nome do usuário", required = true)
    private String firstName;

    @NotBlank(message = "O sobrenome é obrigatório e deve ter no mínimo 3 caracteres")
    @Schema(description = "Sobrenome do usuário", required = true)
    private String lastName;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    @Column(unique = true)
    @Schema(description = "CPF do usuário", required = true)
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "DATE")
    @Schema(description = "Data de nascimento do usuário (formato: dd/MM/yyyy)")
    private LocalDate birthdate;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de email inválido")
    @Column(unique = true)
    @Schema(description = "Endereço de email do usuário", required = true)
    private String email;

    @Schema(description = "CEP do usuário")
    private String cep;

    @NotBlank(message = "A senha é obrigatória e deve ter no mínimo 6 caracteres")
    @Schema(description = "Senha do usuário", required = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Função do usuário (ROLE_USER ou ROLE_ADMIN)", required = true)
    private UserRole userRole;

    @ColumnDefault("true")
    @Schema(description = "Status de ativação da conta do usuário (true ou false)")
    private boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRole == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
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
        return true;
    }

    public String getBirthdateFormatted() {
        return birthdate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}