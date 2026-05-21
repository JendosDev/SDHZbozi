package com.example.sdhzbozi.common.controller;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.dto.auth.LoginRequestDTO;
import com.example.sdhzbozi.common.dto.auth.RegisterRequestDTO;
import com.example.sdhzbozi.common.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Registration form has binding errors"
            );
        }

        if (isLoggedIn(authentication)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User is already authorized" + authentication.getName()
            );
        }

        try {
            return authService.register(form);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "email.exists", e.getMessage());
            throw new IllegalArgumentException("This email already exists");
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<AuthAnswerDTO> login (
            @Valid @RequestBody LoginRequestDTO form,
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        if (isLoggedIn(authentication)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User is already authorized" + authentication.getName()
            );
        }

        return authService.login(form, request, response);
    }

    private boolean isLoggedIn (Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

}
