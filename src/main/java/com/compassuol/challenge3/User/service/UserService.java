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

}
