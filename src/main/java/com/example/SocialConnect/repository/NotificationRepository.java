package com.example.SocialConnect.repository;

import com.example.SocialConnect.model.Notification;
import com.example.SocialConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}