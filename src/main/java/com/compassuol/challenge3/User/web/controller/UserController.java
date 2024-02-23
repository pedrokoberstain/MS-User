package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import com.compassuol.challenge3.User.service.UserService;
import com.compassuol.challenge3.User.web.dto.PasswordUpdateDTO;
import com.compassuol.challenge3.User.web.dto.UserUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        UserCreateDTO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserCreateDTO> login(String email, String password) {
        Optional<UserCreateDTO> user = userService.login(email, password);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCreateDTO> getUserById(@PathVariable Long id) {
        Optional<UserCreateDTO> user = userService.getUserbyId(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO user) {
        Optional<UserUpdateDTO> updatedUser = userService.updateUser(id, user);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO passwordUpdatedDTO) {
        String newPassword = passwordUpdatedDTO.getPassword();
        boolean passwordUpdated = userService.updatePassword(id, newPassword);
        if (passwordUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}