package com.family.hwang.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    private String title;
    private String body;

    @Builder
    public PostCreateRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}