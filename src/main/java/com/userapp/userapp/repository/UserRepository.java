package com.userapp.userapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userapp.userapp.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional <User> findByUserId(String userId);
}
