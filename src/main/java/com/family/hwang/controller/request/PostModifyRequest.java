package com.family.hwang.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostModifyRequest {

    private String title;
    private String body;

    @Builder
    public PostModifyRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
