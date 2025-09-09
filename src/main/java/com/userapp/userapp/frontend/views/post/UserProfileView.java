package com.userapp.userapp.frontend.views.post;

import com.userapp.userapp.frontend.layout.MainLayout;
import com.userapp.userapp.model.User;
import com.userapp.userapp.services.UserService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "user/:id", layout = MainLayout.class)
public class UserProfileView  extends VerticalLayout implements BeforeEnterObserver{

    private final UserService userService;
    private String userId;

    public UserProfileView(UserService userService) {
        this.userService = userService;
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
         userId = event.getRouteParameters().get("id").orElse("");
        User user = userService.getUserById(userId);

        if (user == null) {
            Notification.show("User not found");
            getUI().ifPresent(ui -> ui.navigate("posts"));
            return;
        }

        Image avatar = new Image(user.getPictureUrl(), "Avatar");
        avatar.setWidth("100px");
        avatar.setHeight("100px");
        avatar.getStyle().set("border-radius", "50%");

        add(new H2(user.getLName()), avatar);
        add(new Span("Bio: " + user.getAreaOfInterest()));
        add(new Span("Phone: " + user.getPhoneNum()));

        Anchor callLink = new Anchor("tel:" + user.getPhoneNum(), "Call " + user.getLName());
        callLink.setTarget("_blank");
        callLink.getElement().getThemeList().add("primary");

        add(callLink);
    }
    
}
