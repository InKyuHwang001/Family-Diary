package com.family.hwang.model;

import com.family.hwang.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;

@Getter
public class User {

    private Long id;
    private String username;
    private String password;
    private String email;

    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp removedAt;

    @Builder
    private User(Long id, String username, String password, String email, Timestamp registeredAt, Timestamp updatedAt, Timestamp removedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredAt = registeredAt;
        this.updatedAt = updatedAt;
        this.removedAt = removedAt;
    }


    public static User fromEntity(UserEntity entity){
        return User.builder()
                .id(entity.getId())
                .username(entity.getUserName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .registeredAt(entity.getRegisteredAt())
                .updatedAt(entity.getUpdatedAt())
                .removedAt(entity.getRemovedAt())
                .build();
    }
}
