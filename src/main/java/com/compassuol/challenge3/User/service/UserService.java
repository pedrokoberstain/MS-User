package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import com.compassuol.challenge3.User.web.dto.mapper.DozerMapper;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.repository.UserRepository;
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
        user = repository.save(user);
        return DozerMapper.parseObject(user, UserCreateDTO.class);
    }

    public UserCreateDTO login(String email, String password) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return mapper.map(user.get(), UserCreateDTO.class);
        }
        return null;
    }

    public UserCreateDTO getUserbyId(Long id) {
        Optional<User> user = repository.findById(id);
        return user.map(value -> mapper.map(value, UserCreateDTO.class)).orElse(null);
    }

    public Optional<User> updateUser(Long id, UserCreateDTO newUser) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setCpf(newUser.getCpf());
            user.setBirthdate(newUser.getBirthdate());
            user.setEmail(newUser.getEmail());
            user.setCep(newUser.getCep());
            user.setActive(newUser.isActive());

            // Mapeamos para a entidade User antes de salvar
            User updatedUserEntity = mapper.map(user, User.class);
            User savedUserEntity = repository.save(updatedUserEntity);

            // Mapeamos de volta para UserVO antes de retornar
            return Optional.ofNullable(mapper.map(savedUserEntity, User.class));
        } else {
            return Optional.empty();
        }
    }

    public User updatePassword(Long id, String password) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(password);
            return repository.save(user);
        } else {
            return null;
        }
    }
}

