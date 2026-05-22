package com.example.sdhzbozi.admin.service;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.enums.RoleEnum;
import com.example.sdhzbozi.common.model.Role;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.RoleRepository;
import com.example.sdhzbozi.common.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminUserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AdminUserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public AuthAnswerDTO editUserRole (
            String queryRole,
            Integer id
    ) {
        RoleEnum roleName = stringToRole(queryRole);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This role does not exists: " + roleName
                ));

        User user = dtoToUser(id);

        user.setRole(role);
        userRepository.save(user);

        return userToDTO(user);
    }

    private RoleEnum stringToRole (String role) {
        RoleEnum roleNane;

        try {
            roleNane = RoleEnum.valueOf(role.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This role does not exists: " + role
            );
        }

        return roleNane;

//        switch (role) {
//            case "UNDEFINED": {
//                return RoleEnum.UNDEFINED;
//            }
//            case "ADMIN": {
//                return RoleEnum.ADMIN;
//            }
//            case "CHILD": {
//                return RoleEnum.CHILD;
//            }
//            case "MEMBER": {
//                return RoleEnum.MEMBER;
//            }
//            case null, default: {
//                throw new ResponseStatusException(
//                        HttpStatus.BAD_REQUEST,
//                        "This role does not exists"
//                );
//            }
//        }
    }

    private User dtoToUser (Integer id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found: " + id
                ));
    }

    private AuthAnswerDTO userToDTO (User user) {
        return new AuthAnswerDTO(
                user.getId(),
                user.getFirstname(),
                user.getSurname(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

}
