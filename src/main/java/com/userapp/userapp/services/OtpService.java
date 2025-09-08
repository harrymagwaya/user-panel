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

    //private Otp record = new Otp();


    public String generateAndSendOtp(String phoneNum){

        Optional <Otp> user = otpRepository.findTopByPhoneNumber(phoneNum);
            if (user.isEmpty()) {
                throw new RuntimeException("User not found");
        }
            otpStore.put(phoneNum, otp);

            Otp checkotp = user.get();

            LocalDateTime createdAt = LocalDateTime.now();
            checkotp.setCreatedTime(createdAt);

            LocalDateTime expires = createdAt.plusMinutes(2);
            checkotp.setExpiryTime(expires);
 
            User me = userRepository.findByUserId(phoneNum).orElseThrow(()-> new RuntimeException("user not exist"));
            checkotp.setUserId(me);

            checkotp.setIsUsed(true);

            checkotp.setPhoneNumber(phoneNum);
        
            System.out.println("sent opt " + otp + phoneNum);
            return otp;
    }

    public boolean verifyOtp(String phoneNum, String submittedOtp){
        Optional<Otp> user = otpRepository.findTopByPhoneNumber(phoneNum);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Otp checkotp = user.get();

        if (LocalDateTime.now().isAfter(checkotp.getExpiryTime())) {
            return false;
        }

        if (!checkotp.getCode().equals(submittedOtp)) {
            return false;
        }
       
        checkotp.setIsUsed(true);
        checkotp.setStatus_of_otp(Status_of_otp.USED);
        //otpRepository.save(record);
       otpRepository.save(checkotp);

        return true;
    }

    
}
