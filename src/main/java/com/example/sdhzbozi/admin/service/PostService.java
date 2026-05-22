package com.example.sdhzbozi.admin.service;

import com.example.sdhzbozi.common.cloudinary.CloudinaryImageService;
import com.example.sdhzbozi.common.cloudinary.UploadedImage;
import com.example.sdhzbozi.common.dto.EventDTO;
import com.example.sdhzbozi.common.dto.NewsDTO;
import com.example.sdhzbozi.common.dto.request.EventRequestForm;
import com.example.sdhzbozi.common.dto.request.NewsRequestForm;
import com.example.sdhzbozi.common.model.Department;
import com.example.sdhzbozi.common.model.Event;
import com.example.sdhzbozi.common.model.News;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.DepartmentRepository;
import com.example.sdhzbozi.common.repositories.EventRepository;
import com.example.sdhzbozi.common.repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PostService {

    private final CloudinaryImageService cloudinaryImageService;
    private final NewsRepository newsRepository;
    private final DepartmentRepository departmentRepository;
    private final EventRepository eventRepository;

    public PostService(CloudinaryImageService cloudinaryImageService, NewsRepository newsRepository, DepartmentRepository departmentRepository, EventRepository eventRepository) {
        this.cloudinaryImageService = cloudinaryImageService;
        this.newsRepository = newsRepository;
        this.departmentRepository = departmentRepository;
        this.eventRepository = eventRepository;
    }

    public NewsDTO postNews (
            NewsRequestForm form,
            User user
    ) throws IOException {
        if (form == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "News cannot be empty"
            );
        }

        News news = new News(
                form.title(),
                form.content(),
                LocalDateTime.now(),
                user
        );

        if (form.image() != null) {
            UploadedImage image = cloudinaryImageService.upload(form.image(), "sdh-zbozi/news");
            news.setImageUrl(image.url());
            news.setImagePublicId(image.publicId());
        }

        newsRepository.save(news);

        return newsToDTO(news);
    }

    public EventDTO postEvent (
            EventRequestForm form,
            User user
    ) throws IOException {
        if (form == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Event cannot be empty"
            );
        }

        Department department = departmentRepository.findById(form.departmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department not found: " + form.departmentId()
                ));

        Event event = new Event(
                form.title(),
                form.description(),
                form.time(),
                department,
                user
        );

        if (form.image() != null) {
            UploadedImage image = cloudinaryImageService.upload(form.image(), "sdh-zbozi/events");
            event.setImageUrl(image.url());
            event.setImagePublicId(image.publicId());
        }

        eventRepository.save(event);

        return eventToDTO(event);
    }

    private NewsDTO newsToDTO (News news) {
        return new NewsDTO(
                news.getTitle(),
                news.getContent(),
                news.getCreatedAt(),
                news.getCreatedBy().getId(),
                news.getImageUrl()
        );
    }

    private EventDTO eventToDTO (Event event) {
        return new EventDTO(
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getDepartmentId().getId(),
                event.getCreatedById().getId(),
                event.getImageUrl()
        );
    }

}
