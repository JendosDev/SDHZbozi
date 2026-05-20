package com.example.sdhzbozi.common.dto;

import java.time.LocalDateTime;

public record EventsDTO (
        String title,
        String description,
        LocalDateTime date,
        Integer departmentId,
        Integer createdById
) {
}
