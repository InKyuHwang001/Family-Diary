package com.family.hwang.model;

import com.family.hwang.model.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Post {

    private Long id;
    private String title;
    private String body;
    private User user;

    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;


    @Builder
    private Post(Long id, String title, String body, User user, Timestamp registeredAt, Timestamp updatedAt, Timestamp removedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
        this.removedAt = removedAt;
    }

    public static Post fromEntity(PostEntity entity) {
        return Post.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .user(User.fromEntity(entity.getUser()))
                .registeredAt(entity.getRegisteredAt())
                .updatedAt(entity.getUpdatedAt())
                .removedAt(entity.getRemovedAt())
                .build();
    }
}