package com.userapp.userapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userapp.userapp.model.LoanApplication;
import com.userapp.userapp.model.MyUserDetails;
import com.userapp.userapp.model.User;
import com.userapp.userapp.repository.LoanRepository;
import com.userapp.userapp.repository.UserRepository;


@Service
public class LoanService {
    
    @Autowired
    private UserContextService userContextService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;


    public List<LoanApplication> getMyLoans(){
        User userId = userContextService.getCurrentUserEntity();
        System.out.println("ID IN GET MY LOANS " + userId);

       // User user = userRepository.findByUserId(userId.getUsername().toString()).orElseThrow(()-> new RuntimeException("user cannot be found"));
        return loanRepository.findByRequesterId(userId);
    }

    public void applyLoan(LoanApplication loanApplication){
        String userId = userContextService.getCurrentUserId();
        System.out.println("ID IN LOAN APPLY " + userId);

        User user = userRepository.findByUserId(userId)
                                .orElseThrow(()-> new RuntimeException("user not found"));
         
        LoanApplication loanApplication2 = new LoanApplication();
        loanApplication2.setRequesterId(user);
        loanApplication2.setCreatedAt(LocalDateTime.now());
    }

}
