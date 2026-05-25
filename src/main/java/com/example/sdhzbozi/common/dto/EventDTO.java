package com.example.sdhzbozi.common.dto;

import java.time.LocalDateTime;

public record EventDTO(
        String title,
        String description,
        LocalDateTime date,
        Integer departmentId,
        Integer createdById,
        String imageUrl,
        String location
) {
}
