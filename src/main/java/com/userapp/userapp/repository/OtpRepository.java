package com.userapp.userapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userapp.userapp.model.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    
}
