package com.family.hwang.controller;

import com.family.hwang.controller.request.PostCreateRequest;
import com.family.hwang.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    PostService postService;

    /**
     * 글 작성
     * @throws Exception
     */

    @Test
    @WithMockUser
    public void 글작성_성공() throws Exception {
        String title = "title";
        String body = "body";
        PostCreateRequest request = PostCreateRequest.builder()
                .title(title)
                .body(body)
                .build();


        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
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
}