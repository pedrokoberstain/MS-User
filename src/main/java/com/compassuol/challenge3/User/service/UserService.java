package com.compassuol.challenge3.User.service;

import com.compassuol.challenge3.User.data.vo.v1.UserVO;
import com.compassuol.challenge3.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public UserVO createUser(UserVO user) {
        return repository.save(user);
    }

    public UserVO login(String email, String password) {
        Optional<UserVO> user = repository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }

    public UserVO getUserbyId(Long id) {
        Optional<UserVO> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public Optional<UserVO> updateUser(Long id, UserVO newUser) {
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

    public UserVO updatePassword(Long id, String password) {
        Optional<UserVO> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            UserVO user = optionalUser.get();
            user.setPassword(password);
            return repository.save(user);
        } else {
            return null;
        }
    }
}

