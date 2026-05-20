package com.example.sdhzbozi.common.controller;

import com.example.sdhzbozi.common.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/api/home")
    public Map<String, Object> getHomePage() {
        return Map.of(
                "news", homeService.getNews(),
                "events", homeService.getEvents()
        );
    }

}
