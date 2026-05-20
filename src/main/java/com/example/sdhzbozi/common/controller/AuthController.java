package com.example.sdhzbozi.common.controller;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.dto.auth.RegisterRequestDTO;
import com.example.sdhzbozi.common.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<AuthAnswerDTO> register (
            @Valid @RequestBody RegisterRequestDTO form,
            Authentication authentication
    ) {
        if (authentication.isAuthenticated()){
            throw new IllegalStateException("User is already authorized" + authentication.getName());
        }
        return authService.register(form);
    }

}
