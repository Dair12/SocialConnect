package com.example.SocialConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.SocialConnect.model.Notification;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.UserRepository;
import com.example.SocialConnect.service.LikeService;
import com.example.SocialConnect.service.NotificationService;
import com.example.SocialConnect.service.PostService;
import com.example.SocialConnect.service.SubscriptionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class PageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // templates/register.html
    }

    @GetMapping("/")
    public String home(Model model) {
        // Вот здесь вставляешь получение текущего пользователя
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) auth.getPrincipal();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Добавляем пользователя в модель для шаблона
        model.addAttribute("user", user);

        return "home"; // это имя Thymeleaf-шаблона
    }

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable Long id, Model model, Authentication authentication) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);

        var likedPosts = likeService.getLikedPostDtos(user.getEmail());
        model.addAttribute("likedPosts", likedPosts);

        var myPosts = postService.getUserPosts(user.getId());
        model.addAttribute("myPosts", myPosts);

        // Количество подписчиков
        int followersCount = subscriptionService.getFollowersCount(user.getId());
        model.addAttribute("followersCount", followersCount);

        // Определяем, просматривает ли профиль текущий юзер
        String currentUserEmail = authentication.getName();
        boolean isOwnProfile = currentUserEmail.equals(user.getEmail());
        model.addAttribute("isOwnProfile", isOwnProfile);

        // Показывать ли кнопку "Подписаться"
        boolean isFollowing = subscriptionService.isFollowing(currentUserEmail, user.getId());
        model.addAttribute("isFollowing", isFollowing);

        return "profile"; // либо "user_profile", если шаблон отдельный
    }

    @GetMapping("/create_post")
    public String createPostPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) auth.getPrincipal();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);

        return "create_post";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) auth.getPrincipal();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);

        // Лайкнутые посты
        var likedPosts = likeService.getLikedPostDtos(email);
        model.addAttribute("likedPosts", likedPosts);

        // Мои посты
        var myPosts = postService.getUserPosts(user.getId());
        model.addAttribute("myPosts", myPosts);

        //notifications
        List<Notification> notifications = notificationService.getUserNotifications(user);
    
        model.addAttribute("notifications", notifications);
        model.addAttribute("isOwnProfile", true);
        model.addAttribute("followersCount", subscriptionService.getFollowersCount(user.getId()));
        model.addAttribute("isFollowing", false);

        return "profile";
    }
}

