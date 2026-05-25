package com.example.sdhzbozi.common.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record EventRequestForm (
    String title,
    String description,
    LocalDateTime time,
    Integer departmentId,
    MultipartFile image,
    String location
) {
}
