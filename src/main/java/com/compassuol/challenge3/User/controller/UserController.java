package com.compassuol.challenge3.User.controller;

import com.compassuol.challenge3.User.data.vo.v1.UserVO;
import com.compassuol.challenge3.User.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserVO> getUserbyId(Long id) {
        return ResponseEntity.ok(userService.getUserbyId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVO> updateUser(@PathVariable Long id, @RequestBody UserVO newUser) {
        Optional<UserVO> updatedUser = userService.updateUser(id, newUser);
        return updatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserVO> updatePassword(@PathVariable Long id, @RequestBody String password) {
        UserVO updatedUser = userService.updatePassword(id, password);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
