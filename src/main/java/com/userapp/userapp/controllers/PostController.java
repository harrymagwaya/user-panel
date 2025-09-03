package com.userapp.userapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userapp.userapp.model.Post;
import com.userapp.userapp.repository.PostRepository;
import com.userapp.userapp.services.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/post")
public class PostController {
    

    @Autowired
    private PostService postService;

    @Autowired 
    private PostRepository postRepository;

    
    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
       postService.createPost(post);
        postRepository.save(post);
        return ResponseEntity.ok("Post created succesfully");
    }

    @GetMapping("/all-posts")
    public ResponseEntity<List<Post>> getAllPosts() {

        List<Post> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Post>> getMyPosts() {
        List<Post> posts = postService.getPostsByCurrentUser();
        return ResponseEntity.ok(posts);
    }
    
    
}
