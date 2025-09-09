package com.userapp.userapp.frontend.forms;

import com.userapp.userapp.model.LoanApplication;
import com.userapp.userapp.services.LoanService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("loan-apply")
public class LoanApplicationView extends VerticalLayout {

    private final IntegerField amountField = new IntegerField("Loan Amount (UGX)");
    private final TextArea purposeField = new TextArea("Purpose");
    private final TextArea supporting = new TextArea("Purpose");

    private final Button submitButton = new Button("Submit Application");

    public LoanApplicationView(LoanService loanService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout form = new VerticalLayout();
        form.setWidth("100%");
        form.setMaxWidth("400px");
        form.setPadding(true);
        form.setSpacing(true);
        form.getStyle().set("background-color", "#ffffff");
        form.getStyle().set("border-radius", "8px");
        form.getStyle().set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        form.getStyle().set("padding", "2rem");

        Binder<LoanApplication> binder = new Binder<>(LoanApplication.class);
        LoanApplication request = new LoanApplication();

        binder.forField(amountField)
                .asRequired("Amount is required")
                .withValidator(amount -> amount >= 10000, "Minimum UGX 10,000")
                .bind(LoanApplication::getLoanAmount, LoanApplication::setLoanAmount);

        binder.forField(purposeField)
                .asRequired("Purpose is required")
                .bind(LoanApplication::getReason, LoanApplication::setReason);

        binder.forField(supporting)
                .asRequired("Purpose is required")
                .bind(LoanApplication::getSuportingDocumentsUrl, LoanApplication::setSuportingDocumentsUrl);

    
        submitButton.setWidthFull();
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(e -> {
            if (binder.writeBeanIfValid(request)) {
                loanService.applyLoan(request);
                Notification.show("Loan request submitted");
                getUI().ifPresent(ui -> ui.navigate("loans"));
            } else {
                Notification.show("Please correct the errors", 3000, Notification.Position.MIDDLE);
            }
        });

        form.add(new H3("Apply for a Loan"), amountField, purposeField, submitButton);
        add(form);
    }
}
