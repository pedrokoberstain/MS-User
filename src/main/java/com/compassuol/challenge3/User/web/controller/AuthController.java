package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.infra.mqueue.MessagePublisher;
import com.compassuol.challenge3.User.model.EmissaoNotification;
import com.compassuol.challenge3.User.service.UserService;
import com.compassuol.challenge3.User.web.dto.AuthenticationDto;
import com.compassuol.challenge3.User.web.dto.RegisterDto;
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
public class AuthController {

    @Autowired
    UserService authorizationService;
    private final RabbitTemplate rabbitTemplate;
    private final MessagePublisher messagePublisher;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto authetinticationDto) {
        ResponseEntity<Object> response = authorizationService.login(authetinticationDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            sendNotification(authetinticationDto.getEmail(), "LOGIN");
        }
        return response;
    }

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