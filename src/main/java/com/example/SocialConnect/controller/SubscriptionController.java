package com.example.SocialConnect.controller;

import com.example.SocialConnect.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Long userId, Authentication authentication) {
        String myEmail = authentication.getName();
        boolean result = subscriptionService.subscribe(myEmail, userId);
        return ResponseEntity.ok(result ? "Subscribed" : "Already subscribed");
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam Long userId, Authentication authentication) {
        String myEmail = authentication.getName();
        boolean result = subscriptionService.unsubscribe(myEmail, userId);
        return ResponseEntity.ok(result ? "Unsubscribed" : "Not subscribed");
    }

    @GetMapping("/is-following")
    public ResponseEntity<?> isFollowing(@RequestParam Long userId, Authentication authentication) {
        String myEmail = authentication.getName();
        boolean result = subscriptionService.isFollowing(myEmail, userId);
        return ResponseEntity.ok(result);
    }
}