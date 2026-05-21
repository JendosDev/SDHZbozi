package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.enums.RoleEnum;
import com.example.sdhzbozi.common.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
