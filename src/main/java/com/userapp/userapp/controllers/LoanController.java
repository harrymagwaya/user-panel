package com.userapp.userapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userapp.userapp.model.LoanApplication;
import com.userapp.userapp.model.LoanApplication.Status_of_app;
import com.userapp.userapp.repository.LoanRepository;
import com.userapp.userapp.services.LoanService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/loan")
public class LoanController {
    
    @Autowired
    private  LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/my")
    public ResponseEntity<List<LoanApplication>> getMyLoans() {
        List<LoanApplication> loans = loanService.getMyLoans();
        return ResponseEntity.ok(loans);
    }
    
    @PostMapping("/apply")
    public ResponseEntity<String> getLoans(@RequestBody LoanApplication loanApplication) {
       
        loanService.applyLoan(loanApplication);
        loanApplication.setStatus_of_app(Status_of_app.PENDING);
        loanRepository.save(loanApplication);
        
        return ResponseEntity.ok("Loan applied");
    }
    
}
