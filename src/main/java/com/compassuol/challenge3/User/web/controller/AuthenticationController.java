package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.infra.security.TokenService;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.repository.UserRepository;
import com.compassuol.challenge3.User.web.dto.AuthenticationDTO;
import com.compassuol.challenge3.User.web.dto.LoginResponseDTO;
import com.compassuol.challenge3.User.web.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        Optional<User> existingUser = repository.findByEmail(data.email());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User();
        newUser.setFirstName(data.firstName());
        newUser.setLastName(data.lastName());
        newUser.setCpf(data.cpf());
        newUser.setBirthdate(data.birthdate());
        newUser.setEmail(data.email());
        newUser.setCep(data.cep());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}