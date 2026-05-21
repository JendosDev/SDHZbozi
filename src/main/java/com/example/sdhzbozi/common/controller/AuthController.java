package com.example.sdhzbozi.common.controller;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.dto.auth.LoginRequestDTO;
import com.example.sdhzbozi.common.dto.auth.RegisterRequestDTO;
import com.example.sdhzbozi.common.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
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
            BindingResult result,
            Authentication authentication
    ) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Registration form has binding errors");
        }

        if (authentication.isAuthenticated()){
            throw new IllegalStateException("User is already authorized" + authentication.getName());
        }

        try {
            return authService.register(form);
        } catch (IllegalStateException e) {
            result.rejectValue("email", "email.exists", e.getMessage());
            throw new IllegalArgumentException("This email already exists");
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<AuthAnswerDTO> login (
            @Valid @RequestBody LoginRequestDTO form,
            Authentication authentication
    ) {
        if (authentication.isAuthenticated()) {
            throw new IllegalStateException("User is already authorized" + authentication.getName());
        }

        return authService.login(form);
    }

}
