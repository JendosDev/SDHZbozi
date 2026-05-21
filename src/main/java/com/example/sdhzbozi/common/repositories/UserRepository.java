package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByName(String name);
}
