package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.service.AuthorizationService;
import com.compassuol.challenge3.User.web.dto.AuthenticationDTO;
import com.compassuol.challenge3.User.web.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authetinticationDto) {
        return authorizationService.login(authetinticationDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO registerDto) {
        return authorizationService.register(registerDto);
    }
}