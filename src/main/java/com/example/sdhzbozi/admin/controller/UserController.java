package com.example.sdhzbozi.admin.controller;

import com.example.sdhzbozi.admin.service.AdminService;
import com.example.sdhzbozi.admin.service.AdminUserService;
import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.dto.auth.UserPatchDTO;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.UserRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final AdminUserService adminUserService;

    public UserController(AdminService adminService, UserRepository userRepository, AdminUserService adminUserService) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public Map<String, Object> getApprovingPage(
            Authentication authentication
    ) {
        isAuthorized(authentication);

        return adminService.approvalPage();
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<AuthAnswerDTO> patchUser (
            @RequestBody UserPatchDTO dto,
            @RequestParam Integer id,
            Authentication authentication
    ) {
        isAuthorized(authentication);

        return ResponseEntity.ok(adminUserService.patchUser(dto, id));
    }

    private User isAuthorized (Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user does not have rights to enter admin page: " + authentication.getName()));
    }



}
