package com.family.hwang.controller.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostModifyRequest {

    @NotBlank(message = "Title is blank")
    private String title;
    @NotBlank(message = "Body is blank")
    private String body;

    @Builder
    public PostModifyRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
