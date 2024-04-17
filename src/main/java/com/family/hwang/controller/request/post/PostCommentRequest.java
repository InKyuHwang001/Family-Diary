package com.family.hwang.controller.request.post;

import lombok.Getter;

@Getter
public class PostCommentRequest {

    private String comment;

    public PostCommentRequest(String comment) {
        this.comment = comment;
    }
}
