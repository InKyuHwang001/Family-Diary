package com.family.hwang.repository;

import com.family.hwang.controller.request.post.PostSearch;
import com.family.hwang.model.entity.PostEntity;
import com.family.hwang.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PostEntityRepositoryCustom {
    List<PostEntity> getList(PostSearch postSearch);
    List<PostEntity> getListByUserName(UserEntity userEntity, PostSearch postSearch);
}
