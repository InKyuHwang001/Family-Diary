package com.family.hwang.controller.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2_000;

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}
