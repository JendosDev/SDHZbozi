package com.example.sdhzbozi.common.service;

import com.example.sdhzbozi.common.model.News;
import com.example.sdhzbozi.common.repositories.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final NewsRepository newsRepository;

    public HomeService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getNews() {
        return newsRepository.findAll().stream()
                .sorted()
                .toList();
    }

}
