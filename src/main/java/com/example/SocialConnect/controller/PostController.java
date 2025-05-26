package com.example.SocialConnect.controller;

import com.example.SocialConnect.dto.CreatePostRequest;
import com.example.SocialConnect.dto.PostDto;
import com.example.SocialConnect.model.Post;
import com.example.SocialConnect.service.PostService;

import java.util.List;

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

    @GetMapping("/feed")
    public List<PostDto> getFeed(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "25") int size
    ) {
        return postService.getRandomPosts(page, size);
    }
}