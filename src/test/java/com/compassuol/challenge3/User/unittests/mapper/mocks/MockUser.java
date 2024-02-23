package com.compassuol.challenge3.User.unittests.mapper.mocks;

import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import com.compassuol.challenge3.User.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockUser {

    public User mockEnntity() {
        return mockEntity(0);
    }

    public UserCreateDTO mockVO() {
        return mockVO(0);
    }

    public List<User> mockEntityList() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 14; i++) {
            users.add(mockEntity(i));
        }
        return users;
    }

    public List<UserCreateDTO> mockVOList() {
        List<UserCreateDTO> users = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            users.add(mockVO(i));
        }
        return users;
    }

    public User mockEntity(Integer number) {
        User user = new User();
        user.setActive(true);
        user.setBirthdate("01/01/2000");
        user.setCep("00000-000");
        user.setCpf("000.000.000-00");
        user.setEmail("maria@email.com");
        user.setFirstName("Maria");
        user.setId(number.longValue());
        user.setLastName("Oliveira");
        user.setPassword("12345678");
        return user;
    }

    public UserCreateDTO mockVO(Integer number) {
        UserCreateDTO user = new UserCreateDTO();
        user.setActive(true);
        user.setBirthdate("01/01/2000");
        user.setCep("00000-000");
        user.setCpf("000.000.000-00");
        user.setEmail("joao@email.com");
        user.setFirstName("Joao");
        user.setId(number.longValue());
        user.setLastName("Silva");
        return user;
    }
}
