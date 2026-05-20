package com.example.sdhzbozi.common.service;

import com.example.sdhzbozi.common.dto.EventDTO;
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

    public List<EventDTO> getEvents() {
        return toEventDTO(eventRepository.findAll().stream()
                .sorted()
                .toList()
        );
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

    private List<EventDTO> toEventDTO(List<Event> eventList) {
        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event event : eventList) {
            eventDTOs.add(new EventDTO(
                    event.getTitle(),
                    event.getDescription(),
                    event.getDate(),
                    event.getDepartmentId().getId(),
                    event.getCreatedById().getId()
            ));
        }
        return eventDTOs;
    }

}
