package com.userapp.userapp.model;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name  = "pwd_table")
public class Disability {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dis_id")
    private Long disbId;

    @ManyToOne
    @JoinColumn(name = "person_Id")
    private User user;

    @Column(name = "disability_type")
    private String type;

    @Column(name = "disability_description")
    private String description;


}
