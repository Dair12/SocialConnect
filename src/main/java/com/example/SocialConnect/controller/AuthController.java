package com.example.SocialConnect.controller;

import com.example.SocialConnect.dto.AuthResponse;
import com.example.SocialConnect.dto.LoginRequest;
import com.example.SocialConnect.dto.RegisterRequest;
import com.example.SocialConnect.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);

        // Установить cookie с токеном (пример, без Secure/HttpOnly для теста)
        Cookie cookie = new Cookie("token", authResponse.getToken());
        cookie.setPath("/");
        // cookie.setHttpOnly(true); // рекомендуется для безопасности
        // cookie.setSecure(true);   // только для HTTPS
        cookie.setMaxAge(24 * 60 * 60); // сутки
        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }
}