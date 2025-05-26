package com.example.SocialConnect.repository;

import com.example.SocialConnect.model.Like;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findByUser(User user);
    int countByPost(Post post);
}