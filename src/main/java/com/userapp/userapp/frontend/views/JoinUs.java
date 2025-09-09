package com.userapp.userapp.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("join")
public class JoinUs extends VerticalLayout{
    JoinUs(){
        add(new H1("Join us now through ussd by dailing *****"));
    }
}
