package com.example.SocialConnect.controller;

import com.example.SocialConnect.model.Like;
import com.example.SocialConnect.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<Map<String, Integer>> likePost(@RequestParam Long postId, Authentication auth) {
        likeService.likePost(auth.getName(), postId);
        int likesCount = likeService.getLikeCount(postId);
        return ResponseEntity.ok(Map.of("likes", likesCount));
    }

    @PostMapping("/unlike")
    public ResponseEntity<Map<String, Integer>> unlikePost(@RequestParam Long postId, Authentication auth) {
        likeService.unlikePost(auth.getName(), postId);
        int likesCount = likeService.getLikeCount(postId);
        return ResponseEntity.ok(Map.of("likes", likesCount));
    }

    @GetMapping("/activity")
    public ResponseEntity<List<Long>> likedPosts(Authentication auth) {
        List<Like> likes = likeService.getLikedPosts(auth.getName());
        List<Long> postIds = likes.stream()
            .map(like -> like.getPost().getId())
            .toList();
        return ResponseEntity.ok(postIds);
    }
}