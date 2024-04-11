package com.family.hwang.service;

import com.family.hwang.controller.request.UserLogInRequest;
import com.family.hwang.controller.request.UserSignUpRequest;
import com.family.hwang.excecption.ErrorCode;
import com.family.hwang.excecption.HwangFamilyException;
import com.family.hwang.fixture.UserEntityFixture;
import com.family.hwang.model.entity.UserEntity;
import com.family.hwang.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.family.hwang.excecption.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @MockBean
    BCryptPasswordEncoder encoder;

    /**
     * 화원 가입
     */
    @Test
    void 회원가입_성공() {
        String email = "email.example.com";
        String userName = "userName";
        String password = "password";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .build();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(UserEntityFixture.get(userName, password, email));

        assertDoesNotThrow(() -> userService.signup(request));
    }

    @Test
    void 회원가입_실패_userName_중복() {
        String email = "email@example.com";
        String userName = "userName";
        String password = "password";

        UserSignUpRequest request = UserSignUpRequest.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .build();

        UserEntity fixture = UserEntityFixture.get(userName, password, email);


        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.encode(password)).thenReturn("encrypt_password");
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        HwangFamilyException e = assertThrows(HwangFamilyException.class, () -> userService.signup(request));
        assertEquals(DUPLICATED_USER_NAME, e.getErrorCode());
    }

    /**
     * 로그인
     */
    @Test
    void 로그인이_성공() {
        String userName = "userName";
        String password  = "password";

        UserLogInRequest request = UserLogInRequest.builder()
                .userName(userName)
                .password(password)
                .build();
        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(encoder.matches(password, fixture.getPassword())).thenReturn(true);

        assertDoesNotThrow(() -> userService.login(request));
    }


    @Test
    void 로그인이_실패_존재하지_않는_userName() {
        String userName = "userName";
        String password  = "password";

        UserLogInRequest request = UserLogInRequest.builder()
                .userName(userName)
                .password(password)
                .build();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        HwangFamilyException e = assertThrows(HwangFamilyException.class, () -> userService.login(request));
        assertEquals(USER_NOT_FOUND, e.getErrorCode());

    }

    @Test
    void 로그인이_실패_다른_password() {
        String userName = "userName";
        String password  = "password";
        String wrongPassword  = "wrongPassword";

        UserLogInRequest wrongRequest = UserLogInRequest.builder()
                .userName(userName)
                .password(wrongPassword)
                .build();
        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        HwangFamilyException e = assertThrows(HwangFamilyException.class, () -> userService.login(wrongRequest));
        assertEquals(INVALID_PASSWORD, e.getErrorCode());

    }
}