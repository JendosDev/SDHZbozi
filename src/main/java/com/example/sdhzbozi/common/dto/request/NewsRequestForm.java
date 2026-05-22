package com.example.sdhzbozi.common.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record NewsRequestForm(
        String title,
        String content,
        MultipartFile image
) {
}
