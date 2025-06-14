package com.example.SocialConnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @Lob
    private String imageBase641;

    @Lob
    private String imageBase642;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post() {
        this.createdAt = LocalDateTime.now();
    }

    // getters и setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getImageBase641() { return imageBase641; }
    public void setImageBase641(String imageBase641) { this.imageBase641 = imageBase641; }

    public String getImageBase642() { return imageBase642; }
    public void setImageBase642(String imageBase642) { this.imageBase642 = imageBase642; }
}