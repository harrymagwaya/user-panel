package com.userapp.userapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LogInController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    /*
     * @PostMapping("/login")
     * public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
     * 
     * try {
     * Authentication authentication = authenticationManager.authenticate(new
     * UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
     * loginRequest.getPasword()));
     * 
     * MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
     * 
     * UserCollab user = userDetails.getUser();
     * 
     * return ResponseEntity.ok("Successfull login for " + user.getUserName() +
     * " email " + user.getEmail());
     * } catch (DisabledException e) {
     * return
     * ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account not approved yet");
     * }catch(BadCredentialsException e){
     * return
     * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
     * }
     * 
     */
}
