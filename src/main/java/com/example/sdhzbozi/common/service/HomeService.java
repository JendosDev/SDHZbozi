package com.example.sdhzbozi.common.service;

import com.example.sdhzbozi.common.dto.NewsDTO;
import com.example.sdhzbozi.common.model.Event;
import com.example.sdhzbozi.common.model.News;
import com.example.sdhzbozi.common.repositories.EventRepository;
import com.example.sdhzbozi.common.repositories.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<NewsDTO> getNews() {
        return toNewsDTO(newsRepository.findAll()).stream()
                .sorted()
                .toList();
    }

    public List<Event> getEvents() {
        return eventRepository.findAll().stream()
                .sorted(Comparator.comparing(Event::getDate).reversed())
                .toList();
    }

    private List<NewsDTO> toNewsDTO(List<News> newsList) {
        List<NewsDTO> newsDTOs = new ArrayList<>();
        for (News news : newsList) {
            newsDTOs.add(new NewsDTO(
                    news.getTitle(),
                    news.getContent(),
                    news.getCreatedAt(),
                    news.getCreatedBy().getId()
            ));
        }
        return newsDTOs;
    }

    private List

}
