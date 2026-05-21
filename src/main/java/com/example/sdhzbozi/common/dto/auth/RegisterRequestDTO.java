package com.example.sdhzbozi.common.dto.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequestDTO(
        @NotNull
        String firstname,

        @NotNull
        String surname,

        @NotNull
        @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
        String email,

        @NotNull
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")
        String password
) {
}
