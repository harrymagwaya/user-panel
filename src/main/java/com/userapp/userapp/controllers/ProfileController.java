package com.userapp.userapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userapp.userapp.model.User;
import com.userapp.userapp.repository.UserRepository;
import com.userapp.userapp.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/profile")
public class ProfileController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getMethodName(@PathVariable String id) {
        User user = repository.findByPhoneNum(id).orElseThrow(()-> new RuntimeException("user not found : "));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }
    
}
