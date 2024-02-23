package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import com.compassuol.challenge3.User.web.dto.mapper.DozerMapper;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.repository.UserRepository;
import com.compassuol.challenge3.User.web.dto.mapper.UserUpdateDTO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public UserCreateDTO createUser(UserCreateDTO userVO) {
        User user = DozerMapper.parseObject(userVO, User.class);
        User savedUser = repository.save(user);
        return DozerMapper.parseObject(savedUser, UserCreateDTO.class);
    }

    public Optional<UserCreateDTO> login(String email, String password) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Optional.of(DozerMapper.parseObject(user.get(), UserCreateDTO.class));
        } else {
            return Optional.empty();
        }
    }

    public Optional<UserCreateDTO> getUserbyId(Long id) {
        Optional<User> user = repository.findById(id);
        return user.map(value -> Optional.of(DozerMapper.parseObject(value, UserCreateDTO.class))).orElseGet(Optional::empty);
    }

    public Optional<UserUpdateDTO> updateUser(Long id, UserUpdateDTO newUser) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if (newUser.getFirstName() != null && !newUser.getFirstName().isEmpty()) {
                existingUser.setFirstName(newUser.getFirstName());
            }
            if (newUser.getLastName() != null && !newUser.getLastName().isEmpty()) {
                existingUser.setLastName(newUser.getLastName());
            }
            if (newUser.getEmail() != null && !newUser.getEmail().isEmpty()) {
                existingUser.setEmail(newUser.getEmail());
            }
            if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
                existingUser.setPassword(newUser.getPassword());
            }

            User updatedUser = repository.save(existingUser);
            return Optional.of(DozerMapper.parseObject(updatedUser, UserUpdateDTO.class));
        } else {
            return Optional.empty();
        }
    }


    public boolean updatePassword(Long id, String password) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            user.get().setPassword(password);
            repository.save(user.get());
            return true;
        } else {
            return false;
        }
    }
}

