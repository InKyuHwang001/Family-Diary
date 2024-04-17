package com.family.hwang.service;

import com.family.hwang.controller.request.post.PostCreateRequest;
import com.family.hwang.controller.request.post.PostModifyRequest;
import com.family.hwang.controller.request.post.PostSearch;
import com.family.hwang.excecption.HwangFamilyException;
import com.family.hwang.model.Post;
import com.family.hwang.model.entity.PostEntity;
import com.family.hwang.model.entity.UserEntity;
import com.family.hwang.repository.PostEntityRepository;
import com.family.hwang.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.family.hwang.excecption.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(PostCreateRequest request, String userName) {
        //find user
        var userEntity = getUserEntityOrExceptions(userName);

        PostEntity entity = PostEntity.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .user(userEntity)
                .build();

        postEntityRepository.save(entity);
    }

    @Transactional
    public Post modify(Long postId, PostModifyRequest request, String userName) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        if (postEntity.getUser() != userEntity) {
            throw new HwangFamilyException(INVALID_PERMISSION, String.format("user %s has no permission with post %d", userName, postId));
        }

        postEntity.edit(request.getTitle(), request.getBody());
        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, Long postId) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        if (postEntity.getUser() != userEntity) {
            throw new HwangFamilyException(INVALID_PERMISSION, String.format("user %s has no permission with post %d", userName, postId));
        }

        postEntityRepository.delete(postEntity);
    }

    public List<Post> list(PostSearch postSearch) {
        return postEntityRepository.getList(postSearch).stream()
                .map(Post::fromEntity)
                .collect(Collectors.toList());

    }

    public Page<Post> my(String userName, PostSearch postSearch) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);

//        return postEntityRepository.getListByUserName(userEntity, postSearch).map(Post::fromEntity);
        return null;
    }


    private UserEntity getUserEntityOrExceptions(String userName) {
        return userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new HwangFamilyException(USER_NOT_FOUND, String.format("%s not founded", userName)));
    }

    private PostEntity getPostEntityOrExceptions(Long postId) {
        return postEntityRepository.findById(postId).orElseThrow(() ->
                new HwangFamilyException(POST_NOT_FOUND, String.format("%s not founded", postId)));
    }

}
