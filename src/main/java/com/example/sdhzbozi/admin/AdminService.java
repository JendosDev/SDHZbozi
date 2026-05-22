package com.example.sdhzbozi.admin;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.enums.RoleEnum;
import com.example.sdhzbozi.common.model.Role;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.RoleRepository;
import com.example.sdhzbozi.common.repositories.UserRepository;
import com.example.sdhzbozi.common.service.APIService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final APIService apiService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminService(APIService apiService, UserRepository userRepository, RoleRepository roleRepository) {
        this.apiService = apiService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Map<String, Object> adminPage(User user) {
        return Map.of(
                "admin", userToDTO(user),
                "news", apiService.getNews(),
                "news_count", apiService.getNewsSize(),
                "events", apiService.getEvents(),
                "events_count", apiService.getEventsSize(),
                "users", getUsers(),
                "users_count", getUsersCount()
        );
    }

    public Map<String, Object> approvalPage() {
        return Map.of(
                "undefined_users", getUndefinedUsers(),
                "undefined_users_count", getUndefinedUsersCount()
        );
    }

    private List<AuthAnswerDTO> getUsers () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::userToDTO)
                .toList();
    }

    private List<AuthAnswerDTO> getUndefinedUsers() {
        Role role = roleRepository.findByName(RoleEnum.UNDEFINED)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Default role has not been found: " + RoleEnum.UNDEFINED
                ));
        List<User> users = userRepository.getUsersByRole(role);
        return users.stream()
                .map(this::userToDTO)
                .toList();
    }

    private AuthAnswerDTO userToDTO (User user) {
        return new AuthAnswerDTO(
                user.getId(),
                user.getFirstname(),
                user.getSurname(),
                user.getEmail()
        );
    }

    private Long getUsersCount() {
        return userRepository.count();
    }

    private Long getUndefinedUsersCount() {
        return roleRepository.undefinedUsersCount();
    }

}
