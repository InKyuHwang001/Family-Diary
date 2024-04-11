package com.family.hwang.fixture;

import com.family.hwang.model.entity.UserEntity;

public class UserEntityFixture {
    public static UserEntity get(String userName, String password, String email) {

        return UserEntity.builder()
                .id(1L)
                .userName(userName)
                .password(password)
                .email(email)
                .build();
    }

    public static UserEntity get(String userName, String password) {

        return UserEntity.builder()
                .id(1L)
                .userName(userName)
                .password(password)
                .email("email@example.com")
                .build();
    }
}
