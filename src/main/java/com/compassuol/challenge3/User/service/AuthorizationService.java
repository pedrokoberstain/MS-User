package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.infra.security.TokenService;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.model.UserRole;
import com.compassuol.challenge3.User.repository.UserRepository;
import com.compassuol.challenge3.User.web.dto.AuthenticationDTO;
import com.compassuol.challenge3.User.web.dto.LoginResponseDTO;
import com.compassuol.challenge3.User.web.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    public ResponseEntity<Object> register(RegisterDTO registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.getPassword());

        LocalDate birthdate = LocalDate.parse(registerDto.getBirthdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        User newUser = new User();
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setCpf(registerDto.getCpf());
        newUser.setBirthdate(birthdate);
        newUser.setEmail(registerDto.getEmail());
        newUser.setCep(registerDto.getCep());
        newUser.setPassword(encryptedPassword);
        newUser.setUserRole(UserRole.USER);
        newUser.setActive(true);

        userRepository.save(newUser);

        return ResponseEntity.ok().body("User registered successfully");
    }
}