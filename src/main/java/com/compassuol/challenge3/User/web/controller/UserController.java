package com.compassuol.challenge3.User.web.controller;

import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.service.UserService;
import com.compassuol.challenge3.User.web.dto.UserResponseDto;
import com.compassuol.challenge3.User.web.dto.UserSenhaDto;
import com.compassuol.challenge3.User.web.dto.UserUpdateDto;
import com.compassuol.challenge3.User.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getCpf() == null || userUpdateDto.getBirthdate() == null) {
            return ResponseEntity.badRequest().body("CPF e Birthdate são campos obrigatórios");
        }

        User userToUpdate = userService.getById(id);
        User updatedUser = UserMapper.toEntity(userUpdateDto, userToUpdate);
        updatedUser = userService.update(id, updatedUser);
        return ResponseEntity.ok(UserMapper.toDto(updatedUser));
    }


    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UserSenhaDto dto) {
        userService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.ok("Senha alterada com sucesso");
    }


}