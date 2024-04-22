package com.family.hwang.controller;

import com.family.hwang.controller.request.post.PostCreateRequest;
import com.family.hwang.controller.request.post.PostModifyRequest;
import com.family.hwang.excecption.HwangFamilyRuntimeException;
import com.family.hwang.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.family.hwang.excecption.ErrorCode.*;
import static com.family.hwang.excecption.ErrorCode.INVALID_PERMISSION;
import static com.family.hwang.excecption.ErrorCode.INVALID_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    /**
     * 글 작성
     */

    @Test
    @WithMockUser
    public void 글작성_성공() throws Exception {

        PostCreateRequest request = PostCreateRequest.builder()
                .title("title")
                .body("body")
                .build();


        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 글작성_실패_로그인이_안된_상태() throws Exception {
        String title = "title";
        String body = "body";
        var request = PostCreateRequest.builder()
                .title(title)
                .body(body)
                .build();

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    /**
     * 글 수정
     */

    @Test
    void 글수정_실패_인증되지_않은_경우() throws Exception {
        PostModifyRequest request = PostModifyRequest.builder()
                .title("title_1")
                .body("body_1")
                .build();

        mockMvc.perform(put("/api/v1/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is(INVALID_TOKEN.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 글수정_실패_글이_작성되지_않은_경우() throws Exception {
        PostModifyRequest request = PostModifyRequest.builder()
                .title("title_1")
                .body("body_1")
                .build();

        doThrow(new HwangFamilyRuntimeException(INVALID_PERMISSION)).when(postService).modify(any(), any(), any());

        mockMvc.perform(put("/api/v1/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is(INVALID_PERMISSION.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 글수정_실패_DB에러가_발생한_경우() throws Exception {
        PostModifyRequest request = PostModifyRequest.builder()
                .title("title_1")
                .body("body_1")
                .build();

        doThrow(new HwangFamilyRuntimeException(DATABASE_ERROR)).when(postService).modify(any(), any(), any());

        mockMvc.perform(put("/api/v1/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is(DATABASE_ERROR.getStatus().value()));
    }

    /**
     * 글 삭제
     */
    @Test
    @WithAnonymousUser
    void 글삭제_실패_인증되지_않은_경우() throws Exception {
        mockMvc.perform(delete("/api/v1/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(INVALID_TOKEN.getStatus().value()));
    }

    @Test
    @WithMockUser
    void 글삭제_실패_글이_작성되지_않은_경우() throws Exception {

        doThrow(new HwangFamilyRuntimeException(POST_NOT_FOUND)).when(postService).delete(any(), eq(1L));

        mockMvc.perform(delete("/api/v1/posts/{postId}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(POST_NOT_FOUND.getStatus().value()));
    }
}