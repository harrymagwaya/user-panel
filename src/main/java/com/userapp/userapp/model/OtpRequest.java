package com.userapp.userapp.model;

import lombok.Data;

@Data
public class OtpRequest {
    private String phoneNum;
    private String otp;
}
