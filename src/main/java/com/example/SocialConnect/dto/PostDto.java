package com.example.SocialConnect.dto;

import com.example.SocialConnect.model.Post;

public class PostDto {
    public Long id;
    public String username;
    public String content;
    public String createdAt;

    public static PostDto fromEntity(Post post) {
        PostDto dto = new PostDto();
        dto.id = post.getId();
        dto.username = post.getUser().getUsername();
        dto.content = post.getContent();
        dto.createdAt = post.getCreatedAt().toString();
        return dto;
    }
}
