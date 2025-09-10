package com.userapp.userapp.frontend.views.post;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.userapp.userapp.frontend.layout.MainLayout;
import com.userapp.userapp.model.Post;
import com.userapp.userapp.services.PostService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "posts", layout = MainLayout.class)
public class PostView extends VerticalLayout {


     private final PostService postService;

    public PostView(PostService postService) {
        this.postService = postService;
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        SecurityContext context = SecurityContextHolder.getContext();
        VaadinSession.getCurrent().getSession()
        .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);


        add(new H2("Recent Posts"));

        List<Post> posts = postService.getAllPosts();
        posts.forEach(post -> add(createPostCard(post)));
    }

    private Component createPostCard(Post post) {
        Card card = new Card();
        card.setWidth("500px");
        card.setWidthFull();
        card.getStyle().set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)");
        card.getStyle().set("border-radius", "8px");
        card.getStyle().set("padding", "1rem");
        card.getStyle().set("margin-bottom", "1rem");


        HorizontalLayout header = new HorizontalLayout();
        Image avatar = new Image("/static/3375.jpg.webp", "Avatar");
        avatar.setWidth("50px");
        avatar.setHeight("50px");
        avatar.getStyle().set("border-radius", "50%");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.getStyle().set("flex-wrap", "wrap");

        VerticalLayout meta = new VerticalLayout();
        meta.setSpacing(false);
        meta.setPadding(false);
        meta.add(new Span(post.getAuthor().getLName()));
        meta.add(new Span("Posted on: " + post.getCreatedAt()));
        meta.getStyle().set("font-size", "0.85rem");
        
        header.add(avatar, meta);

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        content.setPadding(false);
        content.add(new H4(post.getTitle()));
        content.add(new Paragraph(post.getPostContent()));
        content.getStyle().set("font-size", "0.95rem");


        Button profileButton = new Button("See Profile", VaadinIcon.USER.create());
        profileButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("user/" + post.getAuthor().getUserId())));

        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println("Auth: " + auth);
        // System.out.println("Principal: " + auth.getPrincipal());
        // System.out.println("Principal class: " + auth.getPrincipal().getClass());

        card.add(header, content, profileButton);
        return card;
    }
}

