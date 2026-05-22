package com.example.sdhzbozi.common.dto;

import org.springframework.web.multipart.MultipartFile;

public record NewsRequestForm(
        String title,
        String content,
        MultipartFile image
) {
}
