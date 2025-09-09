package com.userapp.userapp.services;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.LoggerFactory;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userapp.userapp.model.User;
import com.userapp.userapp.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserContextService userContextService;

    Logger log = LoggerFactory.getLogger(UserService.class);

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id) {
            return userRepository.findByUserId(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String getLink(User user){
        return user.getPhoneNum();
    }

    public boolean checkUserExists(String id){
        return userRepository.existsById(id);
    }

    public void updateUser(User updatedData) {
        String userId = userContextService.getCurrentUserId();

        User existingUser = userRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setLName(updatedData.getLName());
        existingUser.setUserEmail(updatedData.getUserEmail());
        existingUser.setPhoneNum(updatedData.getPhoneNum());
        existingUser.setHasDisability(updatedData.getHasDisability());
        existingUser.setGender(updatedData.getGender());
        existingUser.setFName(updatedData.getFName());
        existingUser.setIncomePerAnnum(updatedData.getIncomePerAnnum());
        existingUser.setUserProffession(updatedData.getUserProffession());

        userRepository.save(existingUser);

        
        log.info("User {} updated profile at {}", userId, LocalDateTime.now());
    }
}
