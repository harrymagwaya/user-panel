package com.userapp.userapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Disability {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disbId;

    @ManyToOne
    @JoinColumn(name = "person_Id")
    private User user;

    private String type;

    private String description;

}
