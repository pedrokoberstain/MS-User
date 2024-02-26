/*package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.service.AuthorizationService;
import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import com.compassuol.challenge3.User.web.dto.UserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Users API", description = "Contém as operações relativas ao domínio usuários. " +
        "Permite que os usuários criem, leiam, atualizem e cancelem usuários")
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private AuthorizationService userService;

    @Operation(summary = "Criar um novo usuário.", description = "Recurso para criar um novo usuário no banco de dados.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserCreateDTO.class)))

            })
    @PostMapping
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        UserCreateDTO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Recuperar informações de um usuário existente.", description = "Recurso para recuperar um usuário existente através do Id."
            , responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserCreateDTO.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<UserCreateDTO> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDTO newUser) {
        Optional<UserUpdateDTO> updatedUser = userService.updateUser(id, newUser);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar a senha de um usuário existente.", description = "Recurso para atualizar a senha de um usuário existente no banco de dados."
            , responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UserUpdateDTO passwordUpdatedDTO) {
        String newPassword = passwordUpdatedDTO.getPassword();
        boolean passwordUpdated = userService.updatePassword(id, newPassword);
        if (passwordUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} */