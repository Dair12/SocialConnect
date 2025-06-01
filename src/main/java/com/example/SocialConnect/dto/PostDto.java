package com.example.SocialConnect.dto;

import java.time.format.DateTimeFormatter;

import com.example.SocialConnect.model.Post;

public class PostDto {
    public Long id;
    public Long userId;
    public String username;
    public String content;
    public String createdAt;
    public boolean likedByMe;
    public int likes;
    public String imageBase641;
    public String imageBase642;

    public static PostDto fromEntity(Post post, boolean likedByMe, int likesCount) {
        PostDto dto = new PostDto();
        dto.id = post.getId();
        dto.userId = post.getUser().getId();
        dto.username = post.getUser().getUsername();
        dto.content = post.getContent();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        dto.createdAt = post.getCreatedAt().format(formatter);
        dto.likedByMe = likedByMe;
        dto.likes = likesCount;
        dto.imageBase641 = post.getImageBase641();
        dto.imageBase642 = post.getImageBase642();
        return dto;
    }
}