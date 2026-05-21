package com.example.sdhzbozi.common.dto.auth;

import jakarta.validation.constraints.Pattern;

public record LoginRequestDTO(
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$")
        String username,

        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")
        String password
) {
}
