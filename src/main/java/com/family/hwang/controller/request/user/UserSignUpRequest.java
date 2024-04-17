package com.family.hwang.controller.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String userName;

    @Builder
    public UserSignUpRequest(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
