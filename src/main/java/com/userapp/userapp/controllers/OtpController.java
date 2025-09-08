package com.userapp.userapp.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userapp.userapp.model.Otp;
import com.userapp.userapp.model.OtpRequest;
import com.userapp.userapp.model.User;
import com.userapp.userapp.model.Otp.Status_of_otp;
import com.userapp.userapp.repository.OtpRepository;
import com.userapp.userapp.repository.UserRepository;
import com.userapp.userapp.services.OtpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/login")
public class OtpController {
    
    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    private OtpRepository otpRepository;

    private Otp otp;


    @GetMapping("/get-otp")
    public ResponseEntity<String> getOtp(@RequestBody String phoneNum) {
        
        Optional<User> user = userRepository.findByUserId(phoneNum);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
        otpService.generateAndSendOtp(user.get().getUserId());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("otp sent to" + phoneNum);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest request) {

        Otp otp =new Otp();
        String phoneNum = request.getPhoneNum();
        String otpCode = request.getOtp();
        if (otp.getCode() == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("no otp found");
        }

        boolean valid = otpService.verifyOtp(otp.getCode(), otpCode);
        if (valid) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("welcome account created");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("wrong otp");
    }
    
    
}
