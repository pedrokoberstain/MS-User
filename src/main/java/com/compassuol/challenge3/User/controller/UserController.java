package com.compassuol.challenge3.User.controller;

import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(String email, String password) {
        User user = userService.login(email, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(Long id) {
        return ResponseEntity.ok(userService.getUserbyId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> updatePassword(Long id, String password) {
        return ResponseEntity.ok(userService.updatePassword(id, password));
    }
}
