package com.example.sdhzbozi.common.controller.pages;

import com.example.sdhzbozi.common.service.APIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    private final APIService APIService;

    public HomeController(APIService APIService) {
        this.APIService = APIService;
    }

    @GetMapping("/api/home")
    public Map<String, Object> getHomePage() {
        return Map.of(
                "news", APIService.getNews(),
                "events", APIService.getEvents()
        );
    }

}
