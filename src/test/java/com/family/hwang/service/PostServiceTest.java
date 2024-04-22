package com.family.hwang.service;

import com.family.hwang.controller.request.post.PostCreateRequest;
import com.family.hwang.controller.request.post.PostModifyRequest;
import com.family.hwang.excecption.ErrorCode;
import com.family.hwang.excecption.HwangFamilyRuntimeException;
import com.family.hwang.fixture.PostEntityFixture;
import com.family.hwang.fixture.UserEntityFixture;
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

        var exception = assertThrows(HwangFamilyRuntimeException.class, () -> postService.create(request, userName));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    /**
     * 글 수정
     */
    @Test
    void 글수정_성공(){
        String tittle = "tittle";
        String body = "body";

        PostModifyRequest request = PostModifyRequest.builder()
                .title(tittle)
                .body(body)
                .build();
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);


        assertDoesNotThrow(() -> postService.modify(postId,request,userName));

    }

    @Test
    void 글수정_실패_존재하지_않는_포스트(){
        String tittle = "tittle";
        String body = "body";

        PostModifyRequest request = PostModifyRequest.builder()
                .title(tittle)
                .body(body)
                .build();
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(any())).thenReturn(Optional.empty());


        var e = assertThrows(HwangFamilyRuntimeException.class, () -> postService.modify(postId, request, userName));
        assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());

    }

    @Test
    void 글수정_실패_없는_권한(){
        String tittle = "tittle";
        String body = "body";

        PostModifyRequest request = PostModifyRequest.builder()
                .title(tittle)
                .body(body)
                .build();
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1L);
        UserEntity writer = UserEntityFixture.get("aaaa","aaaa", 2L);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(any())).thenReturn(Optional.of(postEntity));


        var e = assertThrows(HwangFamilyRuntimeException.class, () -> postService.modify(postId, request, userName));
        assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }
    /**
     * 글 삭제
     */
    @Test
    void 글삭제_성공(){
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        assertDoesNotThrow(() -> postService.delete(userName, postId));
    }

    @Test
    void 글삭제_실패_글이_존재하지_않은_경우(){
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1L);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        var exception = assertThrows(HwangFamilyRuntimeException.class, () -> postService.delete(userName, postId));
        assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void 글삭제_실패_유저의_권한이_없는_경우(){
        String userName = "userName";
        Long postId = 1L;

        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1L);
        UserEntity writer = UserEntityFixture.get("userName1", "aaaaa", 2L);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        var exception = assertThrows(HwangFamilyRuntimeException.class, () -> postService.delete(userName, postId));
        assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
    }

    /**
     * 전체 글 리스트
     */


    /**
     * 내 글 리스트
     */

}