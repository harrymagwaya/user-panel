package com.userapp.userapp.frontend.views.auth;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import com.userapp.userapp.model.Otp;

import com.userapp.userapp.repository.UserRepository;
import com.userapp.userapp.services.CustomUserDetailsService;
import com.userapp.userapp.services.OtpService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;

@Route("verify-otp/:phone")
public class VerifyOtp extends VerticalLayout implements BeforeEnterObserver{
    
    private String phone;
    private TextField otpField = new TextField("Enter OTP");
    private Button verifyButton = new Button("Verify");
    private Label countdownLabel = new Label();
    private Binder<Otp> binder = new Binder<>(Otp.class);
    private Otp request = new Otp();
    

    @Autowired
    private UserRepository repository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private int secondsRemaining = 300;

    public VerifyOtp( OtpService otpService, UserRepository repository) {

        binder.forField(otpField)
                .asRequired("OTP is required")
                .withValidator(code -> code.matches("^\\d{4}$"), "OTP must be 4 digits")
                .bind(Otp::getCode, Otp::setCode);

        verifyButton.addClickListener(event -> {
            if (binder.writeBeanIfValid(request)) {
                
                boolean valid = otpService.verifyOtp(phone, request.getCode());
                if (valid) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

                    Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    Notification.show("Login successful");
                    getUI().ifPresent(ui -> ui.navigate("posts"));
                } else {
                    Notification.show("Invalid or expired OTP", 3000, Notification.Position.MIDDLE);
                }
            } else {
                Notification.show("Please enter a valid OTP", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    private void startCountdown() {
        UI ui = UI.getCurrent();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ui.access(() -> {
                    if (secondsRemaining > 0) {
                        countdownLabel.setText("OTP expires in " + secondsRemaining + " seconds");
                        secondsRemaining--;
                    } else {
                        countdownLabel.setText("OTP expired");
                        verifyButton.setEnabled(false);
                        cancel();
                    }
                });
            }
        }, 0, 1000);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#f8f9fa");

        verifyButton.getStyle().set("font-size", "1rem");

        setWidth("100%");
        setMaxWidth("400px");
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#ffffff");
        getStyle().set("border-radius", "8px");
        getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        getStyle().set("padding", "2rem");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        phone = event.getRouteParameters().get("phone").orElse("");
        phone = phone.trim();
        System.out.println("Looking for : " + phone.toString());

        request.setPhoneNumber(phone);
        if (!repository.existsByPhoneNum(phone)) {
            Notification.show("User doesnt exist", 3000, Notification.Position.MIDDLE);
            getUI().ifPresent(ui -> ui.navigate("join"));
        }
       
        add(new H3("Verifying for " + phone), otpField, verifyButton, countdownLabel);

        startCountdown();
    }
}
