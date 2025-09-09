package com.userapp.userapp.frontend.views.auth;

import com.userapp.userapp.model.Otp;

import com.userapp.userapp.services.OtpService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("/auth/request-otp")
public class LoginView extends VerticalLayout  {
    private TextField phoneField = new TextField("Phone Number");
    private Button requestOtpButton = new Button("Request OTP");

    public LoginView(OtpService otpService) {
        Binder<Otp> binder = new Binder<>(Otp.class);
        Otp request = new Otp();

        binder.forField(phoneField)
              .asRequired("Phone number is required")
              .withValidator(phone -> phone.matches("^07\\d{8}$"), "Invalid Ugandan phone format")
              .bind(Otp::getPhoneNumber, Otp::setPhoneNumber);

        add(phoneField, requestOtpButton);
        

        requestOtpButton.addClickListener(event -> {
            if (binder.writeBeanIfValid(request)) {
                otpService.generateAndSendOtp(request.getPhoneNumber());
                Notification.show("OTP sent to " + request.getPhoneNumber());
                getUI().ifPresent(ui -> ui.navigate("verify-otp/" + request.getPhoneNumber()));
               
            } else {
                Notification.show("Please correct input", 3000, Notification.Position.MIDDLE);
            }
        });


        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#f8f9fa");

        requestOtpButton.getStyle().set("font-size", "1rem");

        setWidth("100%");
        setMaxWidth("400px");
        setPadding(true);
        setSpacing(true);
        getStyle().set("background-color", "#ffffff");
        getStyle().set("border-radius", "8px");
        getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        getStyle().set("padding", "2rem");

        phoneField.setWidthFull();
        phoneField.setPlaceholder("e.g. 0700123456");
        phoneField.getStyle().set("font-size", "1rem");
    }
}


