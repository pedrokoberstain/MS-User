package com.compassuol.challenge3.User.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSenhaDto {
    @NotBlank
    @Size(min = 6)
    private String senhaAtual;
    @NotBlank
    @Size(min = 6)
    private String novaSenha;
    @NotBlank
    @Size(min = 6)
    private String confirmaSenha;
}
