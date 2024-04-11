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

    /**
     * 글 작성
     */
    @Test
    void 글작성_성공() {

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
    void 글작성_실패_없는_userName() {
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

    /**
     * 글 수정
     */
}