package com.family.hwang.controller.response.user;

import com.family.hwang.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private String userName;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getUsername()
        );
    }

}
