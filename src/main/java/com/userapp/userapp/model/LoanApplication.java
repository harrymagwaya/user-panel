package com.userapp.userapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoanApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private long loan_amount;


    @NonNull
    @NotBlank
    private String reason;

    @NonNull
    private String suportingDocumentsUrl;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    @Column(name = "status_of_app", updatable = true)
    private Status_of_app status_of_app;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requesterId;

    private LocalDateTime createdAt;

    @PrePersist
    public void defaultTime(){
        if (createdAt == null) {
            createdAt = createdAt.now();
        }
    }

    public LoanApplication() {
        //TODO Auto-generated constructor stub
    }

    public enum Status_of_app {
        PENDING,
        UNDER_REVIEW,
        APPROVED,
        REJECTED
    }
}
