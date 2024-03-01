package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.exception.SolicitacaoNotificationException;
import com.compassuol.challenge3.User.model.EmissaoNotification;
import com.compassuol.challenge3.User.model.ProtocoloSolicitacaoNotification;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.service.UserService;
import com.compassuol.challenge3.User.web.dto.UserResponseDto;
import com.compassuol.challenge3.User.web.dto.UserSenhaDto;
import com.compassuol.challenge3.User.web.dto.UserUpdateDto;
import com.compassuol.challenge3.User.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users")
@Tag(name = "UserController", description = "Endpoints relacionados aos usuários")
public class UserController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário com base no ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        sendNotification(user.getEmail(), "getUserById");
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza os detalhes de um usuário com base no ID fornecido")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        User userToUpdate = userService.getById(id);
        User updatedUser = UserMapper.toEntity(userUpdateDto, userToUpdate);
        updatedUser = userService.update(id, updatedUser);
        sendNotification(updatedUser.getEmail(), "updateUser");
        return ResponseEntity.ok(UserMapper.toDto(updatedUser));
    }

    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao alterar a senha")
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UserSenhaDto dto) {
        userService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        sendNotification(userService.getById(id).getEmail(), "updatePassword");
        return ResponseEntity.ok("Senha alterada com sucesso");
    }

    @Operation(summary = "Solicitar emissão de notificação", description = "Solicita a emissão de uma notificação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação emitida com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao solicitar a emissão de notificação")
    })
    @PostMapping("/notification")
    public ResponseEntity solicitarEmissao(@RequestBody EmissaoNotification emissaoNotification) {
        try {
            ProtocoloSolicitacaoNotification protocolo = (ProtocoloSolicitacaoNotification) userService
                    .solicitaremissaoNotification(emissaoNotification);
            return ResponseEntity.ok(protocolo);
        } catch (SolicitacaoNotificationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    private void sendNotification(String email, String event) {
        try {
            EmissaoNotification notification = new EmissaoNotification();
            notification.setEmail(email);
            notification.setEvent(event);
            notification.setDate(LocalDateTime.now().toString());

            userService.solicitaremissaoNotification(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}