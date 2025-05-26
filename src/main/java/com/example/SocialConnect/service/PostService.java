package com.example.SocialConnect.service;

import com.example.SocialConnect.model.Post;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.PostRepository;
import com.example.SocialConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(String content, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);
    }
}