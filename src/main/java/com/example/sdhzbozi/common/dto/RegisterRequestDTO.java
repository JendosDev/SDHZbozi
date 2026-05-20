package com.example.sdhzbozi.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDTO(
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$")
        String username,

        @NotNull
        @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
        String email,

        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")
        String password
) {
}
