package com.example.sdhzbozi.admin;

import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final AdminService adminService;

    public AdminController(UserRepository userRepository, AdminService adminService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
    }

    @GetMapping
    public Map<String, Object> getAdminPage(
            Authentication authentication
    ) {
        User user = isAuthorized(authentication);
        return adminService.adminPage(user);
    }

    @GetMapping("/users")
    public Map<String, Object> getApprovingPage(
            Authentication authentication
    ) {
        isAuthorized(authentication);

        return adminService.approvalPage();
    }

    private User isAuthorized (Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user does not have rights to enter admin page: " + authentication.getName()));

    }

}
