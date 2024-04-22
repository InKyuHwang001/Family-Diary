package com.family.hwang.controller;

import com.family.hwang.controller.request.user.UserLogInRequest;
import com.family.hwang.controller.request.user.UserSignUpRequest;
import com.family.hwang.controller.response.Response;
import com.family.hwang.controller.response.user.UserLoginResponse;
import com.family.hwang.controller.response.user.UserSignUpResponse;
import com.family.hwang.model.User;
import com.family.hwang.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public Response<UserSignUpResponse> signup (@RequestBody @Valid UserSignUpRequest request){
        User user = userService.signup(request);

        return Response.success(UserSignUpResponse.fromUser(user));
    }


    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody @Valid UserLogInRequest request) {
        String token = userService.login(request);

        return Response.success(UserLoginResponse.fromString(token));
    }


}
