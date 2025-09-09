package com.userapp.userapp.frontend.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.userapp.userapp.frontend.layout.MainLayout;
import com.userapp.userapp.model.Disability;
import com.userapp.userapp.model.MyUserDetails;
import com.userapp.userapp.model.User;
import com.userapp.userapp.services.DisabilityService;
import com.userapp.userapp.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route(value = "disability-form",layout = MainLayout.class)
public class DisabilityFormView extends VerticalLayout {

    private final DisabilityService disabilityService;
    private final User currentUser;
     private List<DisabilityEntry> entries = new ArrayList<>();
     private VerticalLayout entryContainer = new VerticalLayout();
     private Button addButton = new Button("Add Another");
     private Button submitButton = new Button("Submit All");

    private Select<String> typeSelect = new Select<>();
   


    public DisabilityFormView(DisabilityService disabilityService, @AuthenticationPrincipal UserService userService, @AuthenticationPrincipal MyUserDetails userdetails) {
        this.disabilityService = disabilityService;
        String username = userdetails.getUsername();
        this.currentUser = userService.getUserById(username);

      
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout form = new VerticalLayout();
        form.setWidth("100%");
        form.setMaxWidth("600px");
        form.setPadding(true);
        form.setSpacing(true);
        form.getStyle().set("background-color", "#ffffff");
        form.getStyle().set("border-radius", "8px");
        form.getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        form.getStyle().set("padding", "2rem");

        form.add(new H3("Disability Information"));
        form.add(entryContainer, addButton, submitButton);

        addButton.addClickListener(e -> addDisabilityEntry());
        submitButton.addClickListener(e -> submitAll());

        addDisabilityEntry(); // Start with one entry
        add(form);
    }

    private void addDisabilityEntry() {
        Select<String> typeSelect = new Select<>();
        typeSelect.setLabel("Type of Disability");
        typeSelect.setItems(
            "Visual Impairment",
            "Hearing Impairment",
            "Physical Disability",
            "Cognitive/Developmental",
            "Mental Health",
            "Other"
        );
        typeSelect.setPlaceholder("Select type");
        typeSelect.setWidthFull();

        TextArea otherField = new TextArea("Please specify");
        otherField.setVisible(false);
        otherField.setWidthFull();

        typeSelect.addValueChangeListener(e -> {
            otherField.setVisible("Other".equals(e.getValue()));
        });

        TextArea descriptionField = new TextArea("Description");
        descriptionField.setWidthFull();

        Button removeButton = new Button("Remove");
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        VerticalLayout entryLayout = new VerticalLayout(
            new H4("Disability #" + (entries.size() + 1)),
            typeSelect,
            otherField,
            descriptionField,
            removeButton
        );
        entryLayout.setPadding(false);
        entryLayout.setSpacing(true);
        entryLayout.getStyle().set("border", "1px solid #ccc");
        entryLayout.getStyle().set("border-radius", "6px");
        entryLayout.getStyle().set("padding", "1rem");
        entryLayout.getStyle().set("margin-bottom", "1rem");

        DisabilityEntry entry = new DisabilityEntry(typeSelect, otherField, descriptionField);
        entries.add(entry);
        entryContainer.add(entryLayout);

        removeButton.addClickListener(ev -> {
            entryContainer.remove(entryLayout);
            entries.remove(entry);
        });
    }

    private void submitAll() {
        List<Disability> disabilities = new ArrayList<>();

        for (DisabilityEntry entry : entries) {
            String type = entry.typeSelect.getValue();
            if ("Other".equals(type)) {
                type = "Other: " + entry.otherField.getValue();
            }

            Disability disability = new Disability();
            disability.setUser(currentUser);
            disability.setType(type);
            disability.setDescription(entry.descriptionField.getValue());
          

            disabilities.add(disability);
        }

        disabilityService.saveDisability(disabilities);
        Notification.show("Disability details saved");
        getUI().ifPresent(ui -> ui.navigate("profile"));
    }

    private static class DisabilityEntry {
        Select<String> typeSelect;
        TextArea otherField;
        TextArea descriptionField;
        
        DisabilityEntry(Select<String> typeSelect, TextArea otherField, TextArea descriptionField) {
            this.typeSelect = typeSelect;
            this.otherField = otherField;
            this.descriptionField = descriptionField;
        }
    
}
}
