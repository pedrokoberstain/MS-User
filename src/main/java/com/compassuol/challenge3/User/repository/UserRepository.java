package com.compassuol.challenge3.User.repository;

import com.compassuol.challenge3.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    UserDetails findByEmail(String email);

    Optional<User> findById(Long id);


}
