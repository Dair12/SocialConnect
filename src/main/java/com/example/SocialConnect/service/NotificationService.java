package com.example.SocialConnect.service;

import com.example.SocialConnect.model.Notification;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(User user, String message, Long postId) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setPostId(postId);
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(User user) {
        List<Notification> notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user);
        return notifications != null ? notifications : java.util.Collections.emptyList();
    }

    public void markAsRead(Long notificationId) {
        Notification notif = notificationRepository.findById(notificationId).orElse(null);
        if (notif != null && !notif.isRead()) {
            notif.setRead(true);
            notificationRepository.save(notif);
        }
    }
}