package com.example.sdhzbozi.common.service;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.dto.auth.RegisterRequestDTO;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<AuthAnswerDTO> register (
        RegisterRequestDTO form
    ) {
        if (form == null) {
            throw new IllegalArgumentException("Registration form is empty");
        }

        if (userRepository.existsUsersByEmail(form.email())) {
            throw new IllegalArgumentException("This email has already been registered: " + form.email());
        }

        if (userRepository.existsUserByName(form.username())){
            throw new IllegalArgumentException("This username has already been registered: " + form.username());
        }

        User user = new User(
                form.username(),
                form.email(),
                passwordEncoder.encode(form.password())
        );
        userRepository.save(user);

        return ResponseEntity.ok(toAuthDTO(user));
    }

    private AuthAnswerDTO toAuthDTO (User user) {
        return new AuthAnswerDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

}
