package com.example.sdhzbozi.common.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NewsDTO (
        @NotNull
        String title,

        String content,

        LocalDateTime createdAt,
        Integer createdById,

        String imageUrl
) {
}
