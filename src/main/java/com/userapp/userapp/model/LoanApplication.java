package com.userapp.userapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table(name = "loan_details")
public class LoanApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "")
    private Long id;

    @NotEmpty
    @Column(name = "loan_amount")
    private long loanAmount;


    @NonNull
    @NotBlank
    @Column(name = "reason")
    private String reason;

    @NonNull
    @Column(name = "supporting_documents")
    private String suportingDocumentsUrl;

    @Enumerated(EnumType.STRING)
    @NotEmpty
    @Column(name = "status_of_app", updatable = true)
    private Status_of_app status_of_app;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    //@Column(name = "")
    private User requesterId;

 
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private Long approvedBy;

    @Column(nullable = true)
    private Long rejectedBy;

    @PrePersist
    public void defaultTime(){
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
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
