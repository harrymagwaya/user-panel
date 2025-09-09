package com.userapp.userapp.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.userapp.userapp.services.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/auth/","/UIDL/**", "/auth/request-otp", "/auth/verify-otp/:phone").permitAll()
                        .requestMatchers(req-> isVaadinInternalRequest(req)).permitAll()
                        .requestMatchers("/VAADIN/**",
                        "/frontend/**",
                        "/images/**",
                        "/manifest.webmanifest",
                        "/sw.js",
                        "/offline.html"
                        )
                .permitAll()
                        .anyRequest()
                        .authenticated()
        )
        .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                //.formLogin(login -> login.disable());

        return http.build();

    }

    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

     private static boolean isVaadinInternalRequest(HttpServletRequest req){
                String param = req.getParameter("v-r");

                return param != null;
    }
}

