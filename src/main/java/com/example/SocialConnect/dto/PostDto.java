package com.example.SocialConnect.dto;

import com.example.SocialConnect.model.Post;

public class PostDto {
    public Long id;
    public String username;
    public String content;
    public String createdAt;
    public boolean likedByMe;
    public int likes;

    public static PostDto fromEntity(Post post, boolean likedByMe, int likesCount) {
        PostDto dto = new PostDto();
        dto.id = post.getId();
        dto.username = post.getUser().getUsername();
        dto.content = post.getContent();
        dto.createdAt = post.getCreatedAt().toString();
        dto.likedByMe = likedByMe;
        dto.likes = likesCount;
        return dto;
    }
}