package com.family.hwang.service;

import com.family.hwang.controller.request.UserLogInRequest;
import com.family.hwang.controller.request.UserSignUpRequest;
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

import java.util.Optional;

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

    @Transactional
    public User signup(UserSignUpRequest request) {

        //check whether userName is duplicated or not
        userEntityRepository.findByUserName(request.getUserName()).ifPresent(it -> {
            throw new HwangFamilyException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", request.getUserName()));
        });

        //sign up
        UserEntity entity = userEntityRepository.save(UserEntity.of(request.getUserName(),
                encoder.encode(request.getPassword()),
                request.getEmail()));

        return User.fromEntity(entity);
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
