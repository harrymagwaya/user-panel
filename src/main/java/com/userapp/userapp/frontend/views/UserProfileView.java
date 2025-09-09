package com.userapp.userapp.frontend.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.userapp.userapp.frontend.layout.MainLayout;
import com.userapp.userapp.model.Disability;
import com.userapp.userapp.model.MyUserDetails;
import com.userapp.userapp.model.User;
import com.userapp.userapp.services.DisabilityService;
import com.userapp.userapp.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "profile", layout = MainLayout.class)

public class UserProfileView extends VerticalLayout {
    @Autowired
    private final UserService userService;
    @Autowired
    private final User currentUser;

    @Autowired
    private final DisabilityService disabilityService;

    private TextField nameField = new TextField("Full Name");
    private EmailField emailField = new EmailField("Email");
    private TextField phoneField = new TextField("Phone Number");
    private TextField nationalidField = new TextField("National Id");
    private TextField genderField = new TextField("Gender");
    private DatePicker dob = new DatePicker("DOB");
    private TextField profField = new TextField("Profession");
    private NumberField incomeField = new NumberField("Income per Anum");
    private Checkbox disabilityCheckbox = new Checkbox("I have a disability");
    private Button saveButton = new Button("Save");

    public UserProfileView(UserService userService, @AuthenticationPrincipal MyUserDetails userDetails) {
        this.userService = userService;
        String id = userDetails.getUsername();
        this.currentUser = userService.getUserById(id);
        this.disabilityService = new DisabilityService();
        List<Disability> disabilities = disabilityService.findByUser(currentUser);
 
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout form = new VerticalLayout();
        form.setWidth("100%");
        form.setMaxWidth("500px");
        form.setPadding(true);
        form.setSpacing(true);
        form.getStyle().set("background-color", "#ffffff");
        form.getStyle().set("border-radius", "8px");
        form.getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        form.getStyle().set("padding", "2rem");

        nameField.setValue(currentUser.getLName());
        emailField.setValue(currentUser.getUserEmail());
        phoneField.setValue(currentUser.getPhoneNum());
        nationalidField.setValue(currentUser.getNationalId());
        genderField.setValue(currentUser.getGender());
        dob.setValue(currentUser.getDateOfBirth());
        profField.setValue(currentUser.getUserProffession());
        incomeField.setValue(currentUser.getIncomePerAnnum());
        disabilityCheckbox.setValue(currentUser.getHasDisability());


        nameField.setWidthFull();
        emailField.setWidthFull();
        phoneField.setWidthFull();
        disabilityCheckbox.getStyle().set("margin-top", "1rem");

        saveButton.setWidthFull();
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> {
            currentUser.setLName(nameField.getValue());
            currentUser.setUserEmail(emailField.getValue());
            currentUser.setPhoneNum(phoneField.getValue());
            currentUser.setNationalId(nationalidField.getValue());
            currentUser.setGender(genderField.getValue());
            currentUser.setDateOfBirth(dob.getValue());
            currentUser.setUserProffession(profField.getValue());
            currentUser.setIncomePerAnnum(incomeField.getValue());
            currentUser.setHasDisability(disabilityCheckbox.getValue());

            userService.updateUser(currentUser);
            Notification.show("Profile updated");

            if (disabilityCheckbox.getValue()) {
                getUI().ifPresent(ui -> ui.navigate("disability-form"));
            }
        });

        form.add(new H3("Your Profile"), nameField, emailField, phoneField, disabilityCheckbox, saveButton);

        VerticalLayout disabilitySection = new VerticalLayout();
        disabilitySection.setWidthFull();
        disabilitySection.setPadding(true);
        disabilitySection.setSpacing(true);

            disabilitySection.add(new H4("Your Submitted Disabilities"));

            if (disabilities.isEmpty()) {
                disabilitySection.add(new Span("No disability information submitted."));
            } else {
                for (Disability d : disabilities) {
                    VerticalLayout card = new VerticalLayout();
                    card.getStyle().set("border", "1px solid #ccc");
                    card.getStyle().set("border-radius", "6px");
                    card.getStyle().set("padding", "1rem");
                    card.getStyle().set("margin-bottom", "1rem");

                    card.add(
                        new Span("Type: " + d.getType()),
                        new Span("Description: " + d.getDescription())
                    );

                    disabilitySection.add(card);
                }
            }

        add(form, disabilitySection);
    }
}
