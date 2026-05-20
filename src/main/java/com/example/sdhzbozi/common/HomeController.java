package com.example.sdhzbozi.common;

import com.example.sdhzbozi.common.service.HomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
                "title", "SDH Úbislavice",
                "subtitle", "Spolek hasičů a dětský kroužek",
                    "news", homeService.getNews()
                );
    }

}
