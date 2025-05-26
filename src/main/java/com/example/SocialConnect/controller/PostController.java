package com.example.SocialConnect.controller;

import com.example.SocialConnect.dto.CreatePostRequest;
import com.example.SocialConnect.model.Post;
import com.example.SocialConnect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request, Authentication authentication) {
        String userEmail = authentication.getName();
        Post post = postService.createPost(request.content, userEmail);
        return ResponseEntity.ok(post);
    }

    // Можно добавить другие методы — получение всех постов, удаление и т.д.
}