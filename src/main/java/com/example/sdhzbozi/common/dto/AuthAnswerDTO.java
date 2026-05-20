package com.example.sdhzbozi.common.dto;

import java.net.http.HttpResponse;

public record AuthAnswerDTO(
        Integer id,
        String username,
        String email,
        String status,
        String message
) {
}
