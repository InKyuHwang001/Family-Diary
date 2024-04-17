package com.family.hwang.controller.response.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginResponse {

    private String token;

    public static UserLoginResponse fromString(String token){
        return new UserLoginResponse(token);
    }
}

