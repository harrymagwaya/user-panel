package com.userapp.userapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userapp.userapp.model.Post;
import com.userapp.userapp.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor(User user);
    
}
