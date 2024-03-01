package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.exception.EntityNotFoundException;
import com.compassuol.challenge3.User.exception.PasswordInvalidException;
import com.compassuol.challenge3.User.exception.ResourceNotFoundEx;
import com.compassuol.challenge3.User.exception.SolicitacaoNotificationException;
import com.compassuol.challenge3.User.infra.mqueue.MessagePublisher;
import com.compassuol.challenge3.User.infra.security.TokenService;
import com.compassuol.challenge3.User.model.EmissaoNotification;
import com.compassuol.challenge3.User.model.ProtocoloSolicitacaoNotification;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.model.UserRole;
import com.compassuol.challenge3.User.repository.UserRepository;
import com.compassuol.challenge3.User.web.dto.AuthenticationDto;
import com.compassuol.challenge3.User.web.dto.LoginResponseDto;
import com.compassuol.challenge3.User.web.dto.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private final MessagePublisher messagePublisher;

    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        ResponseEntity<Object> response = ResponseEntity.ok(new LoginResponseDto(token));

        if (response.getStatusCode().is2xxSuccessful()) {
            sendNotification(data.getEmail(), "LOGIN");
        }

        return response;
    }


    public ResponseEntity<Object> register(RegisterDto registerDto) {
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

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundEx("User not found")
        );
    }

    public User update(Long id, User user) {
        User userToUpdate = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundEx("User not found")
        );

        BeanUtils.copyProperties(user, userToUpdate, "id", "password");

        return userRepository.save(userToUpdate);
    }

    @Transactional(readOnly = true)
    public User buscarPorId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public User editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        User user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    public Object solicitaremissaoNotification(EmissaoNotification emissaoNotification) {
        try {
            messagePublisher.solicitarNotification(emissaoNotification);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoNotification(protocolo);
        } catch (Exception e) {
            throw new SolicitacaoNotificationException(e.getMessage());
        }
    }


    private void sendNotification(String email, String event) {
        EmissaoNotification notification = new EmissaoNotification();
        notification.setEmail(email);
        notification.setEvent(event);
        notification.setDate(LocalDateTime.now().toString());

        solicitaremissaoNotification(notification);
    }
}