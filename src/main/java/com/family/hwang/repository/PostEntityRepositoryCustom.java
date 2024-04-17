package com.family.hwang.repository;

import com.family.hwang.controller.request.post.PostSearch;
import com.family.hwang.model.entity.PostEntity;

import java.util.List;

public interface PostEntityRepositoryCustom {
    List<PostEntity> getList(PostSearch postSearch);
}
