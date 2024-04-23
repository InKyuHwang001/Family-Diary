package com.family.hwang.controller.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserSignUpRequest {

    @NotBlank(message = "user name is not blank")
    @Length(min = 5, max = 20, message = "user name length is between 5 and 20")
    private String userName;

    @Email(message = "email is not fit with policy")
    private String email;

    @NotBlank(message = "password is not blank")
    @Length(min = 5, max = 20, message = "password length is between 5 and 20")
    private String password;


    @Builder
    public UserSignUpRequest(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
