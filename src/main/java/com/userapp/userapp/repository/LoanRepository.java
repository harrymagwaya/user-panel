package com.userapp.userapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userapp.userapp.model.LoanApplication;
import com.userapp.userapp.model.User;

@Repository
public interface LoanRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByRequesterId(User user);
    
}
