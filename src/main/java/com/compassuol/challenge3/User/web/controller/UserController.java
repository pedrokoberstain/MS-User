package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.service.UserService;
import com.compassuol.challenge3.User.web.dto.UserResponseDTO;
import com.compassuol.challenge3.User.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService authorizationService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = authorizationService.getById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }
}