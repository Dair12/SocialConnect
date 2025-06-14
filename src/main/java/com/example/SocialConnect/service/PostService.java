package com.example.SocialConnect.service;

import com.example.SocialConnect.model.Post;
import com.example.SocialConnect.model.Subscription;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.LikeRepository;
import com.example.SocialConnect.repository.PostRepository;
import com.example.SocialConnect.repository.SubscriptionRepository;
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

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private NotificationService notificationService;

    public Post createPost(String content, String imageBase641, String imageBase642, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setContent(content);
        post.setImageBase641(imageBase641);
        post.setImageBase642(imageBase642);
        post.setUser(user);
        Post savedPost = postRepository.save(post);

        // Уведомления для подписчиков
        List<Subscription> followers = subscriptionRepository.findByFollowing(user);
        for (Subscription sub : followers) {
            notificationService.createNotification(
                sub.getFollower(),
                user.getUsername() + " опубликовал(а) новый пост",
                savedPost.getId()
            );
        }

        return savedPost;
    }

    public List<PostDto> getRandomPosts(int page, int size, User currentUser) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.getContent().stream()
            .map(post -> PostDto.fromEntity(
                post,
                likeRepository.findByUserAndPost(currentUser, post).isPresent(),
                likeRepository.countByPost(post) // ← получаем количество лайков
            ))
            .collect(Collectors.toList());
    }

    public List<PostDto> getUserPosts(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(post -> PostDto.fromEntity(
                        post,
                        false,
                        likeRepository.countByPost(post)
                ))
                .collect(Collectors.toList());
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public int getLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return 0;
        return likeRepository.countByPost(post);
    }
}