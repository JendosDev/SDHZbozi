package com.example.sdhzbozi.admin;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.UserRepository;
import com.example.sdhzbozi.common.service.APIService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final APIService apiService;
    private final UserRepository userRepository;

    public AdminService(APIService apiService, UserRepository userRepository) {
        this.apiService = apiService;
        this.userRepository = userRepository;
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

    private AuthAnswerDTO userToDTO (User user) {
        return new AuthAnswerDTO(
                user.getId(),
                user.getFirstname(),
                user.getSurname(),
                user.getEmail()
        );
    }

    private List<AuthAnswerDTO> getUsers () {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::userToDTO)
                .toList();
    }

    private Long getUsersCount() {
        return userRepository.count();
    }

}
