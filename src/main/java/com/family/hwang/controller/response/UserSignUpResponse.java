package com.family.hwang.controller.response;

import com.family.hwang.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignUpResponse {

    private String email;
    private String userName;


    @Builder
    private UserSignUpResponse(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public static UserSignUpResponse fromUser(User user){
        return UserSignUpResponse.builder()
                .userName(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
