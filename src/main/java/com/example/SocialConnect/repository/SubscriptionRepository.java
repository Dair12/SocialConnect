package com.example.SocialConnect.repository;

import com.example.SocialConnect.model.Subscription;
import com.example.SocialConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByFollowerAndFollowing(User follower, User following);
    List<Subscription> findByFollower(User follower);   // На кого подписан
    List<Subscription> findByFollowing(User following); // Кто подписан на пользователя

    @Transactional
    void deleteByFollowerAndFollowing(User follower, User following);
}