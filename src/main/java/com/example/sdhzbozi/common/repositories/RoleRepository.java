package com.example.sdhzbozi.common.repositories;

import com.example.sdhzbozi.common.enums.RoleEnum;
import com.example.sdhzbozi.common.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);

    @Query("""
            SELECT COUNT(u)
            FROM User u
            WHERE u.role.name = com.example.sdhzbozi.common.enums.RoleEnum.UNDEFINED
            """)
    Long undefinedUsersCount();
}
