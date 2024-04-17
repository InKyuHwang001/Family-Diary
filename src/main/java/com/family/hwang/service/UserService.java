package com.family.hwang.service;

import com.family.hwang.controller.request.user.UserLogInRequest;
import com.family.hwang.controller.request.user.UserSignUpRequest;
import com.family.hwang.excecption.ErrorCode;
import com.family.hwang.excecption.HwangFamilyException;
import com.family.hwang.model.User;
import com.family.hwang.model.entity.UserEntity;
import com.family.hwang.repository.UserEntityRepository;
import com.family.hwang.util.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.family.hwang.excecption.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUserName(String userName){
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(()->
                new HwangFamilyException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
    }

    @Transactional
    public User signup(UserSignUpRequest request) {

        //check whether userName is duplicated or not
        userEntityRepository.findByUserName(request.getUserName()).ifPresent(it -> {
            throw new HwangFamilyException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", request.getUserName()));
        });

        //encrypting password
        String encryptedPassword = encoder.encode(request.getPassword());

        //sign up
        UserEntity entity = UserEntity.builder()
                .userName(request.getUserName())
                .password(encryptedPassword)
                .email(request.getEmail())
                .build();

        UserEntity saved = userEntityRepository.save(entity);

        return User.fromEntity(saved);
    }

    public String login(UserLogInRequest request) {

        UserEntity userEntity = userEntityRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new HwangFamilyException(USER_NOT_FOUND, String.format("%s not founded", request.getUserName())));

        if (!encoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new HwangFamilyException(INVALID_PASSWORD);
        }

        //TODO: 토큰 생성
        String token = JwtTokenUtils.generateToken(request.getUserName(), secretKey, expiredTimeMs);

        return token;
    }
}
