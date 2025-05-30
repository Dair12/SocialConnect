package com.example.SocialConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.UserRepository;
import com.example.SocialConnect.service.LikeService;
import com.example.SocialConnect.service.PostService;

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

        return "profile";
    }
}

