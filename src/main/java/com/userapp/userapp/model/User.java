package com.userapp.userapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @NonNull
    private String fName;

    @NonNull
    private String lName;

    @Email
    private String userEmail;

    @NonNull
    private String nationalId;

    @NonNull
    private String userDescription;

    @NonNull
    private String userProffession;

    @NonNull
    private BigDecimal  incomePerAnnum;

    private String pictureUrl;

    @NonNull
    private Date dateOfBirth;

    @NonNull
    private String academicQualification;

    @NonNull
    private String areaOfInterest;

    @NonNull
    private String gender;

    @NonNull
    public Boolean hasDisability;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Disability> disabilities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List <Post> posts = new ArrayList<>();

}
