package com.example.SocialConnect.service;

import com.example.SocialConnect.dto.PostDto;
import com.example.SocialConnect.model.Like;
import com.example.SocialConnect.model.Post;
import com.example.SocialConnect.model.User;
import com.example.SocialConnect.repository.LikeRepository;
import com.example.SocialConnect.repository.PostRepository;
import com.example.SocialConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public void likePost(String userEmail, Long postId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (likeRepository.findByUserAndPost(user, post).isEmpty()) {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
        }
    }

    public void unlikePost(String userEmail, Long postId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        likeRepository.findByUserAndPost(user, post).ifPresent(likeRepository::delete);
    }

    public List<Like> getLikedPosts(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return likeRepository.findByUser(user);
    }

    public int getLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return likeRepository.countByPost(post);
    }

    public List<PostDto> getLikedPostDtos(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Like> likes = likeRepository.findByUser(user);
        return likes.stream()
                .map(like -> {
                    Post post = like.getPost();
                    int likesCount = likeRepository.countByPost(post);
                    // true, потому что мы сами смотрим лайкнутые
                    return PostDto.fromEntity(post, true, likesCount);
                })
                .toList();
    }
}