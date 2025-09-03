package com.userapp.userapp.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userapp.userapp.model.Otp;
import com.userapp.userapp.model.User;
import com.userapp.userapp.model.Otp.Status_of_otp;
import com.userapp.userapp.repository.OtpRepository;
import com.userapp.userapp.repository.UserRepository;


@Service
public class OtpService {

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    UserRepository userRepository;

    private final Random random = new Random();

    private final String otp = String.format("%04d", random.nextInt(9999));

    private final Map<String, String> otpStore = new HashMap<>();

    Otp record = new Otp();


    public String generateAndSendOtp(String phoneNum){

        Optional <User> user = userRepository.findByUserId(phoneNum);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found");
        }
            otpStore.put(phoneNum, otp);

            LocalDateTime createdAt = LocalDateTime.now();
            record.setCreatedTime(createdAt);
 
            record.setCode(otp);

            record.setUserId(phoneNum);
        
            System.out.println("sent opt " + otp + phoneNum);
            return otp;
    }

    public boolean verifyOtp(String phoneNum, String otp){
        // Optional<User> user = userRepository.findByPhoneNumber(phoneNum);
        // if (user.isEmpty()) {
        //     throw new RuntimeException("User not found");
        // }
      
   
        // if (LocalDateTime.now().isAfter(expiryTime)) {
        //     record.setStatus_of_otp(Status_of_otp.EXPIRED);
        // }
        record.setIsUsed(true);
        record.setStatus_of_otp(Status_of_otp.USED);
        //otpRepository.save(record);
        return otp.equals(otp);
    }

    
}
