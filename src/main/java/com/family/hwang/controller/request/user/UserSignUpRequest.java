package com.family.hwang.controller.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserSignUpRequest {

    @NotBlank
    @Length(min = 5, max = 20)
    private String userName;

    @Email
    private String email;

    @NotBlank
    @Length(min = 5, max = 20)
    private String password;


    @Builder
    public UserSignUpRequest(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
