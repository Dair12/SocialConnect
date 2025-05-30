package com.example.SocialConnect.model;

import jakarta.persistence.*;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"})
)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Кто подписался
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    // На кого подписан
    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    // Геттеры и сеттеры...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getFollower() { return follower; }
    public void setFollower(User follower) { this.follower = follower; }

    public User getFollowing() { return following; }
    public void setFollowing(User following) { this.following = following; }
}