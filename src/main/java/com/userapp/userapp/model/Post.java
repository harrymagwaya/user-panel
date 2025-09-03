package com.userapp.userapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @NonNull
    private String title;

    @NonNull
    private String postContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    @NonNull
    private PostType postType;

    @Nonnull
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id ")
    private User author;

    @PrePersist
    public void getDefaulTimestamp() {
        if (createdAt == null) {
            createdAt = Timestamp.valueOf(LocalDateTime.now());
        }
        postType = PostType.NORMAL;

    }

    public Post() {
        //TODO Auto-generated constructor stub
    }

    public enum PostType {
        NORMAL,
        INTERVENTION,
        ANNOUNCEMENT
    }

}
