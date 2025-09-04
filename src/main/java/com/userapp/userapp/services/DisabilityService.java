package com.userapp.userapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userapp.userapp.model.Disability;
import com.userapp.userapp.model.User;
import com.userapp.userapp.repository.DisabilityRepository;
import com.userapp.userapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class DisabilityService {
    
    @Autowired
    private UserContextService userContextService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisabilityRepository disabilityRepository;

    @Transactional
    public void saveDisability(List <Disability> disabilities){
        String userId = userContextService.getCurrentUserId();

        User user = userRepository.findByUserId(userId)
                                    .orElseThrow(()-> new RuntimeException("User not found"));

        if (!user.hasDisability) {
            throw new IllegalStateException("User opted not to prov");
        }

        for (Disability disability : disabilities) {  
            disability.setUser(user);
        }

        disabilityRepository.saveAll(disabilities);
    }


    public List<Disability> getDisabilities(){
        String userId = userContextService.getCurrentUserId();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return disabilityRepository.findByUser(user);

    }
}
