package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.model.Role;
import com.example.sdhzbozi.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> getUsersByRole(Role role);

    Optional<User> findUserById(Integer id);
}
