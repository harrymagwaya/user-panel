package com.userapp.userapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table(name = "otp_table")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long otpId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private String phoneNumber;

    @NonNull
    @Column(name = "code")
    private String code;

    @NonNull
    @Column(name = "created_at")
    private LocalDateTime createdTime;

    @NonNull
    @Column(name = "expires_at")
    private LocalDateTime expiryTime;

    @Column(name = "is_entered") 
    private Boolean isUsed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_of_otp")
    private Status_of_otp status_of_otp;

    public static enum Status_of_otp{
        PENDING,
        USED,
        EXPIRED
    }


    @PrePersist
    public void default_time_STATUS(){
        createdTime = LocalDateTime.now();
        if (expiryTime == null) {
            expiryTime = createdTime.plusMinutes(2);
        }

        if (status_of_otp == null) {
            status_of_otp = Status_of_otp.PENDING;
        }
    }

   
    @Override
    public String toString(){
        return "id" + getOtpId() + getCode() +  getCreatedTime() + getIsUsed() + getExpiryTime() + getStatus_of_otp();
    }


    public Otp() {
        //TODO Auto-generated constructor stub
    }
}
