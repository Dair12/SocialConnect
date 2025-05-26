package com.example.SocialConnect.service;

import com.example.SocialConnect.model.Post;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.PostRepository;
import com.example.SocialConnect.repository.UserRepository;
import com.example.SocialConnect.dto.PostDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    public List<PostDto> getRandomPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Post> posts = postRepository.findAll(pageable);
        // Можно random, если хочешь: postRepository.findRandom(pageable); — но обычно просто свежие
        return posts.stream().map(PostDto::fromEntity).toList();
    }
}