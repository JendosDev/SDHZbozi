package com.example.sdhzbozi.common.service;

import com.example.sdhzbozi.common.dto.auth.AuthAnswerDTO;
import com.example.sdhzbozi.common.dto.auth.LoginRequestDTO;
import com.example.sdhzbozi.common.dto.auth.RegisterRequestDTO;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.RoleRepository;
import com.example.sdhzbozi.common.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    public ResponseEntity<AuthAnswerDTO> register (
        RegisterRequestDTO form
    ) {
        if (form == null) {
            throw new IllegalArgumentException("Registration form is empty");
        }

        if (userRepository.existsByEmail(form.email())) {
            throw new IllegalArgumentException("This email has already been registered: " + form.email());
        }

        User user = new User(
                form.firstname(),
                form.surname(),
                form.email(),
                passwordEncoder.encode(form.password()),
                roleRepository
        );
        userRepository.save(user);

        return ResponseEntity.ok(toAuthDTO(user));
    }

    public ResponseEntity<AuthAnswerDTO> login (
            LoginRequestDTO form,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
       User user = userRepository.findByEmail(form.email())
               .orElseThrow(() -> new ResponseStatusException(
                       HttpStatus.UNAUTHORIZED,
                       "Invalid email or password"
               ));

       if (!passwordEncoder.matches(form.password(), user.getPasswordHash())) {
           throw new ResponseStatusException(
                   HttpStatus.UNAUTHORIZED,
                   "Invalid email or password"
           );
       }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.email(),
                        form.password()
                )
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok(toAuthDTO(user));
    }

    private AuthAnswerDTO toAuthDTO (User user) {
        return new AuthAnswerDTO(
                user.getId(),
                user.getFirstname(),
                user.getEmail()
        );
    }

}
