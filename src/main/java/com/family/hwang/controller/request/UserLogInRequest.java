package com.family.hwang.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserLogInRequest {

    private String userName;
    private String password;

    @Builder
    public UserLogInRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
