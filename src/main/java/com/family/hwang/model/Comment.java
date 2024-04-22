package com.family.hwang.model;

import com.family.hwang.model.entity.CommentEntity;
import com.family.hwang.model.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Comment {

    private Long id;
    private String comment;
    private User user;
    private Post post;

    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    @Builder
    private Comment(Long id, String comment, User user, Post post, Timestamp registeredAt, Timestamp updatedAt, Timestamp removedAt) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
        this.removedAt = removedAt;
    }

    public static Comment fromEntity(CommentEntity commentEntity) {
        return Comment.builder()
                .id(commentEntity.getId())
                .comment(commentEntity.getComment())
                .user(User.fromEntity(commentEntity.getUser()))
                .post(Post.fromEntity(commentEntity.getPost()))
                .registeredAt(commentEntity.getRegisteredAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .removedAt(commentEntity.getRemovedAt())
                .build();
    }
}