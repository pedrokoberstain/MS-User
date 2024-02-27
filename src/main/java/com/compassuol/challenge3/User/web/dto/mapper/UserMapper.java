package com.compassuol.challenge3.User.web.dto.mapper;

import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.web.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class UserMapper {

    public static UserResponseDTO toDto(User usuario) {
        String role = usuario.getUserRole().getRole();
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UserResponseDTO.class);
    }
}
