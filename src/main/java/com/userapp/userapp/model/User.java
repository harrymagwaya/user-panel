package com.userapp.userapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private String userId;

    
    private String fName;

    
    private String lName;

  
    private String phoneNum;

    @Column(name = "email_")
    private String userEmail;

    
    private String nationalId;

  
    private String userDescription;

   
    private String userProffession;

    
    private double  incomePerAnnum;

    private String pictureUrl;

    
    private LocalDate dateOfBirth;

    
    private String academicQualification;

    @Column(name = "areao_of_interest")
    private String areaOfInterest;

   @Column(name = "gender_")
    private String gender;

    @NonNull
    public Boolean hasDisability;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Disability> disabilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private List <Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "userId")
    private List<Otp> otps = new ArrayList<>();

    @OneToOne(mappedBy = "requesterId")
    private LoanApplication loan;

    User(){

    }

}
