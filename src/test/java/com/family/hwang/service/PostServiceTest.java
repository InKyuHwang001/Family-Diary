package com.family.hwang.service;

import com.family.hwang.controller.request.PostCreateRequest;
import com.family.hwang.excecption.ErrorCode;
import com.family.hwang.excecption.HwangFamilyException;
import com.family.hwang.model.entity.PostEntity;
import com.family.hwang.model.entity.UserEntity;
import com.family.hwang.repository.PostEntityRepository;
import com.family.hwang.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @MockBean
    UserEntityRepository userEntityRepository;

    @MockBean
    PostEntityRepository postEntityRepository;


    @Test
    void 포스트_생성시_정상동작한다() {

        String tittle = "tittle";
        String body = "body";
        String userName = "userName";

        PostCreateRequest request = PostCreateRequest.builder()
                .title(tittle)
                .body(body)
                .build();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        assertDoesNotThrow(() -> postService.create(request, userName));
    }


    @Test
    void 포스트생성시_유저가_존재하지_않으면_에러를_내뱉는다() {
        String tittle = "tittle";
        String body = "body";
        String userName = "userName";

        PostCreateRequest request = PostCreateRequest.builder()
                .title(tittle)
                .body(body)
                .build();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        var exception = assertThrows(HwangFamilyException.class, () -> postService.create(request, userName));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

}