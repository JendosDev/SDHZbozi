package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUsersByEmail(String email);

    boolean existsUserByName(String name);

    User findUserByName(String name);
}
