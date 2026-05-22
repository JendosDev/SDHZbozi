package com.example.sdhzbozi.admin.service;

import com.example.sdhzbozi.common.cloudinary.CloudinaryImageService;
import com.example.sdhzbozi.common.cloudinary.UploadedImage;
import com.example.sdhzbozi.common.dto.NewsDTO;
import com.example.sdhzbozi.common.dto.NewsRequestForm;
import com.example.sdhzbozi.common.model.News;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.NewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PostService {

    private final CloudinaryImageService cloudinaryImageService;
    private final NewsRepository newsRepository;

    public PostService(CloudinaryImageService cloudinaryImageService, NewsRepository newsRepository) {
        this.cloudinaryImageService = cloudinaryImageService;
        this.newsRepository = newsRepository;
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

    private NewsDTO newsToDTO (News news) {
        return new NewsDTO(
                news.getTitle(),
                news.getContent(),
                news.getCreatedAt(),
                news.getCreatedBy().getId(),
                news.getImageUrl()
        );
    }

}
