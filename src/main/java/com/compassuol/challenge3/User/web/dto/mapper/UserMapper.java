package com.compassuol.challenge3.User.web.dto.mapper;

import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.web.dto.UserResponseDto;
import com.compassuol.challenge3.User.web.dto.UserUpdateDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDate;

public class UserMapper {

    public static UserResponseDto toDto(User usuario) {
        String role = usuario.getUserRole().getRole();
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UserResponseDto.class);
    }

    public static User toEntity(UserUpdateDto userUpdateDto, User user) {
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setCpf(userUpdateDto.getCpf());
        user.setBirthdate(LocalDate.parse(userUpdateDto.getBirthdate()));
        user.setEmail(userUpdateDto.getEmail());
        user.setCep(userUpdateDto.getCep());
        user.setActive(userUpdateDto.isActive());
        return user;
    }
}
