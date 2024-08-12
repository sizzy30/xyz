package com.trial.UserRepository;

import com.trial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String Username);
    Optional<User> findByEmailOrUsername(String email,String Username);
}