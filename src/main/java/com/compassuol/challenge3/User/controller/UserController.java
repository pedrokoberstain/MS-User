package com.compassuol.challenge3.User.controller;

import com.compassuol.challenge3.User.data.vo.v1.UserVO;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserVO> createUser(@Valid @RequestBody UserVO user) {
        UserVO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserVO> login(String email, String password) {
        UserVO user = userService.login(email, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVO> getUserbyId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserbyId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserVO UserVO) {
        Optional<User> updatedUser = userService.updateUser(id, UserVO);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest request) {
        boolean passwordUpdated = userService.updatePassword(id, request.getPassword()).isActive();
        if (passwordUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Getter
    @Setter
    public static class PasswordUpdateRequest {
        private String password;
    }
}