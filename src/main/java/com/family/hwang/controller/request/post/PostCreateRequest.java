package com.family.hwang.controller.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "Title is blank")
    private String title;
    @NotBlank(message = "Body is blank")
    private String body;

    @Builder
    public PostCreateRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}