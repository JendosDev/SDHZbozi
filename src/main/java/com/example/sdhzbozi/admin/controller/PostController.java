package com.example.sdhzbozi.admin.controller;

import com.example.sdhzbozi.admin.service.PostService;
import com.example.sdhzbozi.common.dto.EventDTO;
import com.example.sdhzbozi.common.dto.NewsDTO;
import com.example.sdhzbozi.common.dto.request.EventRequestForm;
import com.example.sdhzbozi.common.dto.request.NewsRequestForm;
import com.example.sdhzbozi.common.model.User;
import com.example.sdhzbozi.common.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class PostController {

    private final UserRepository userRepository;
    private final PostService postService;

    public PostController(UserRepository userRepository, PostService postService) {
        this.userRepository = userRepository;
        this.postService = postService;
    }

    @PostMapping("/news")
    public ResponseEntity<NewsDTO> postNews (
            @ModelAttribute NewsRequestForm form,
            Authentication authentication
    ) throws IOException {
        User user = isAuthorized(authentication);

        return ResponseEntity.ok(postService.postNews(form, user));
    }

    @PostMapping("/events")
    public ResponseEntity<EventDTO> postEvent (
            @ModelAttribute EventRequestForm form,
            Authentication authentication
    ) {
        User user = isAuthorized(authentication);

        return ResponseEntity.ok(postService);
    }

    private User isAuthorized (Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user does not have rights to enter admin page: " + authentication.getName()));
    }

}
