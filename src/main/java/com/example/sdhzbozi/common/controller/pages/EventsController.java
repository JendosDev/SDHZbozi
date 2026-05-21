package com.example.sdhzbozi.common.controller.pages;

import com.example.sdhzbozi.common.service.APIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EventsController {

    private final APIService apiService;

    public EventsController(APIService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/api/events")
    public Map<String, Object> getEventPage() {
        return Map.of(
                "events", apiService.getEvents(),
                "count", apiService.getEvents()
        );
    }

}
