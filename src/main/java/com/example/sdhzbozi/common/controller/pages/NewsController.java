package com.example.sdhzbozi.common.controller.pages;

import com.example.sdhzbozi.common.service.APIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NewsController {

    private final APIService apiService;

    public NewsController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/api/news")
    public Map<String, Object> getNewsPage () {
        return Map.of(
                "news", apiService.getNews(),
                "count", apiService.getNewsSize()
        );
    }

}
