package com.userapp.userapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userapp.userapp.model.Post;
import com.userapp.userapp.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCreatedBy(User user);
    
}
