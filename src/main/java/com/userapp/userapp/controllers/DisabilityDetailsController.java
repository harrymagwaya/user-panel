package com.userapp.userapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userapp.userapp.model.Disability;
import com.userapp.userapp.repository.DisabilityRepository;
import com.userapp.userapp.services.DisabilityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user/disabilities")
public class DisabilityDetailsController {

    private final DisabilityRepository disabilityRepository;

    @Autowired
    private DisabilityService disabilityService;


    DisabilityDetailsController(DisabilityRepository disabilityRepository) {
        this.disabilityRepository = disabilityRepository;
    }

    
    @GetMapping("/my")
    public ResponseEntity<List<Disability>> getMyDisabilities() {
        List <Disability> myDisabilities = disabilityService.getDisabilities();
        return ResponseEntity.ok(myDisabilities);
    }

    @PostMapping("/add-disability")
    public RequestEntity<?> postMethodName(@RequestBody List<Disability> entity) {
        disabilityService.saveDisability(entity);
        disabilityRepository.saveAll(entity);
        return ResponseEntity;
    }
    
    
}
