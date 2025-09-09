package com.userapp.userapp.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userapp.userapp.model.Post;
import com.userapp.userapp.model.User;
import com.userapp.userapp.repository.PostRepository;
import com.userapp.userapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserContextService userContext;

    public void createPost(Post Post){
        String userId = userContext.getCurrentUserId();

        User user = userRepository.findByUserId(userId)
                                        .orElseThrow(()-> new RuntimeException("cannot find user"));

        Post post2 = new Post();
        post2.setAuthor(user);
        post2.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public List<Post> getPostsByCurrentUser() {
        String userId = userContext.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return postRepository.findByAuthor(user);
    }

}
