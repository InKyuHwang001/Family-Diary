package com.family.hwang.fixture;

import com.family.hwang.model.entity.PostEntity;
import com.family.hwang.model.entity.UserEntity;

public class PostEntityFixture {
    public static PostEntity get(String userName, Long postId, Long userId) {
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .userName(userName)
                .build();

        return PostEntity.builder()
                .user(userEntity)
                .id(postId)
                .build();
    }
}
