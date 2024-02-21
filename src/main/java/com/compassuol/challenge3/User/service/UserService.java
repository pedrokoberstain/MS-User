package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.model.User;
import com.compassuol.challenge3.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public User login(String email, String password) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }

    public User getUserbyId(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public Optional<User> updateUser(Long id, User newUser) {
        return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setCpf(newUser.getCpf());
                    user.setBirthdate(newUser.getBirthdate());
                    user.setEmail(newUser.getEmail());
                    user.setCep(newUser.getCep());
                    user.setActive(newUser.isActive());
                    return repository.save(user);
                });
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

