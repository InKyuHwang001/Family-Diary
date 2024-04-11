package com.family.hwang.controller;

import com.family.hwang.controller.request.UserLogInRequest;
import com.family.hwang.controller.request.UserSignUpRequest;
import com.family.hwang.excecption.HwangFamilyException;
import com.family.hwang.model.User;
import com.family.hwang.repository.UserEntityRepository;
import com.family.hwang.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static com.family.hwang.excecption.ErrorCode.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void clean(){
        userEntityRepository.deleteAll();
    }

    /**
     * 회원 가입
     */
    @Test
    public void 회원가입_성공() throws Exception {

        String userName = "name";
        String password = "password";
        String email = "email@example.com";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post("/api/v1/users/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입_실패_중복된_아이디사용() throws Exception {

        String userName = "name";
        String password = "password";
        String email = "email@example.com";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .build();
        userService.signup(request);


        mockMvc.perform(post("/api/v1/users/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    /**
     * 로그인 성공
     */
    @Test
    public void 로그인_성공() throws Exception {
        String userName = "userName";
        String password = "password";
        String email = "email@example.com";

        UserSignUpRequest signUpRequest = UserSignUpRequest.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .build();
        userService.signup(signUpRequest);

        UserLogInRequest request = UserLogInRequest.builder()
                .userName(userName)
                .password(password)
                .build();

        //expected
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 로그인_실패_회원가입이_안된_userName_사용_에러반환() throws Exception {
        String userName = "userName";
        String password = "password";

        UserLogInRequest request = UserLogInRequest.builder()
                .userName(userName)
                .password(password).build();

        //expected
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인_실패_잘못된_password_사용_에러반환() throws Exception {
        String userName = "userName";
        String password = "password";
        String wrongPassword = "wrong";
        String email = "email@example.com";

        UserSignUpRequest signUpRequest = UserSignUpRequest.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .build();
        userService.signup(signUpRequest);

        UserLogInRequest request = UserLogInRequest.builder()
                .userName(userName)
                .password(wrongPassword)
                .build();


        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }



}