package com.userapp.userapp.frontend.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.userapp.userapp.frontend.layout.MainLayout;
import com.userapp.userapp.model.LoanApplication;
import com.userapp.userapp.services.LoanService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "loans", layout = MainLayout.class)
public class LoanListView extends VerticalLayout{
    @Autowired
     private  LoanService loanService;
    public LoanListView(LoanService loanService) {
            this.loanService = loanService;
            setSizeFull();
            setPadding(true);
            setSpacing(true);

            Grid<LoanApplication> grid = new Grid<>(LoanApplication.class, false);
            grid.setItems(loanService.getMyLoans());

            grid.addColumn(LoanApplication::getStatus_of_app).setHeader("Status").setAutoWidth(true);
            grid.addColumn(LoanApplication::getLoanAmount).setHeader("Amount (UGX)").setAutoWidth(true);
            grid.addColumn(LoanApplication::getCreatedAt).setHeader("Created At").setAutoWidth(true);
            grid.addColumn(LoanApplication::getReason).setHeader("Reason").setFlexGrow(1);

            grid.setItemDetailsRenderer(createDetailsRenderer());

            Button applyButton = new Button("Get a Loan", VaadinIcon.PLUS.create());
            applyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            applyButton.getStyle().set("margin-bottom", "1rem");
            applyButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("loan-apply")));

            add(new H2("Loan Applications"),applyButton, grid);
        }

        private ComponentRenderer<Component, LoanApplication> createDetailsRenderer() {
            return new ComponentRenderer<>(loan -> {
                VerticalLayout details = new VerticalLayout();
                details.setPadding(true);
                details.setSpacing(true);

                details.add(new H3("Loan Details"));
                details.add(new Span("Loan ID: " + loan.getLoanId()));
                details.add(new Span("Amount: UGX " + loan.getLoanAmount()));
                details.add(new Span("Created At: " + loan.getCreatedAt()));
                details.add(new Span("Reason: " + loan.getReason()));
                details.add(new Span("Status: " + loan.getStatus_of_app()));
                details.add(new Span("Approval Notes: " + loan.getRejectedBy()));
                details.add(new Span("Approval Notes: " + loan.getApprovedBy()));

                return details;
            });
        }
}
