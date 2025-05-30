package com.example.SocialConnect.service;

import com.example.SocialConnect.model.Subscription;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.SubscriptionRepository;
import com.example.SocialConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean subscribe(String followerEmail, Long followingId) {
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("Following user not found"));

        if (subscriptionRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            return false; // Уже подписан
        }
        Subscription subscription = new Subscription();
        subscription.setFollower(follower);
        subscription.setFollowing(following);
        subscriptionRepository.save(subscription);
        return true;
    }

    public boolean unsubscribe(String followerEmail, Long followingId) {
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> new RuntimeException("Follower not found"));
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("Following user not found"));

        subscriptionRepository.deleteByFollowerAndFollowing(follower, following);
        return true;
    }

    public int getFollowersCount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return subscriptionRepository.findByFollowing(user).size();
    }

    public boolean isFollowing(String followerEmail, Long followingId) {
        User follower = userRepository.findByEmail(followerEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return subscriptionRepository.findByFollowerAndFollowing(follower, following).isPresent();
    }
}