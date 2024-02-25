package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.exception.EntityNotFoundException;
import com.compassuol.challenge3.User.exception.UsernameUniqueViolationException;
import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.repository.UserRepository;
import com.compassuol.challenge3.User.web.dto.UserCreateDTO;
import com.compassuol.challenge3.User.web.dto.UserUpdateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public UserCreateDTO createUser(UserCreateDTO userVO) {
        try {
            User user = modelMapper.map(userVO, User.class);
            User savedUser = repository.save(user);
            return modelMapper.map(savedUser, UserCreateDTO.class);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", userVO.getUsername()));
        }
    }

    public Optional<UserCreateDTO> getUserById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.map(value -> Optional.ofNullable(modelMapper.map(value, UserCreateDTO.class)))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Usuário com id %d não encontrado", id)));
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
            return Optional.of(modelMapper.map(updatedUser, UserUpdateDTO.class));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário com username '%s' não encontrado", username)));
    }
}