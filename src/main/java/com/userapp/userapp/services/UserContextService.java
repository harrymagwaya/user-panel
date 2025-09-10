
package com.userapp.userapp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.userapp.userapp.model.MyUserDetails;
import com.userapp.userapp.model.User;

@Service
public class UserContextService {

    @Autowired
    private final CustomUserDetailsService customUserDetailsService;

    public UserContextService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public MyUserDetails getCurrentUserDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }
        

        Object principal = auth.getPrincipal();

        //System.out.println(principal);

        if (principal instanceof MyUserDetails) {
            return (MyUserDetails) principal;
        }

        if (principal instanceof String) {
            // Fallback: reload full user details using username
            return (MyUserDetails) customUserDetailsService.loadUserByUsername((String) principal);
        }

        if (principal instanceof MyUserDetails) {
            return (MyUserDetails) principal;
        }

        throw new RuntimeException("Unexpected principal type: " + principal.getClass());
    }


    public String getCurrentUserId() {
        return getCurrentUserDetails().getUsername();
    }

    public User getCurrentUserEntity() {
        return getCurrentUserDetails().getUser();
    }
    
}