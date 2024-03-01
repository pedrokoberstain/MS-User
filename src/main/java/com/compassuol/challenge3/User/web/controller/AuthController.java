package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.infra.mqueue.MessagePublisher;
import com.compassuol.challenge3.User.model.EmissaoNotification;
import com.compassuol.challenge3.User.service.UserService;
import com.compassuol.challenge3.User.web.dto.AuthenticationDto;
import com.compassuol.challenge3.User.web.dto.RegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Endpoints relacionados à autenticação")
public class AuthController {

    @Autowired
    UserService authorizationService;
    private final RabbitTemplate rabbitTemplate;
    private final MessagePublisher messagePublisher;
    private final UserService userService;

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário com base nas credenciais fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        ResponseEntity<Object> response = authorizationService.login(authenticationDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            sendNotification(authenticationDto.getEmail(), "LOGIN");
        }
        return response;
    }

    @Operation(summary = "Registrar usuário", description = "Registra um novo usuário com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        ResponseEntity<Object> response = authorizationService.register(registerDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            sendNotification(registerDto.getEmail(), "REGISTER");
        }
        return response;
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