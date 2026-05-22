package com.example.sdhzbozi.common.dto;

import java.time.LocalDateTime;

public record NewsDTO (
        String title,
        String content,
        LocalDateTime createdAt,
        Integer createdById,
        String imageUrl
) {
}
