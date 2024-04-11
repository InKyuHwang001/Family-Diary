package com.family.hwang.controller;

import com.family.hwang.controller.request.PostCreateRequest;
import com.family.hwang.repository.PostEntityRepository;
import com.family.hwang.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void clean() {
        postEntityRepository.deleteAll();
    }


    @Test
    @WithMockUser
    public void 글작성_성공() throws Exception {
        String title = "title";
        String body = "body";
        var request = PostCreateRequest.builder()
                .title(title)
                .body(body)
                .build();

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 포스트작성시_실패_로그인이_안된_상태() throws Exception {
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


}