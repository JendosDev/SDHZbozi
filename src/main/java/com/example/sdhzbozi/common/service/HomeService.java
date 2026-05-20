package com.example.sdhzbozi.common.service;

import com.example.sdhzbozi.common.model.Event;
import com.example.sdhzbozi.common.model.News;
import com.example.sdhzbozi.common.repositories.EventRepository;
import com.example.sdhzbozi.common.repositories.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class HomeService {

    private final NewsRepository newsRepository;
    private final EventRepository eventRepository;

    public HomeService(NewsRepository newsRepository, EventRepository eventRepository) {
        this.newsRepository = newsRepository;
        this.eventRepository = eventRepository;
    }

    public List<News> getNews() {
        return newsRepository.findAll().stream()
                .sorted(Comparator.comparing(News::getCreatedAt).reversed())
                .toList();
    }

    public List<Event> getEvents() {
        return eventRepository.findAll().stream()
                .sorted(Comparator.comparing(Event::getDate).reversed())
                .toList();
    }

    private List<>
}
