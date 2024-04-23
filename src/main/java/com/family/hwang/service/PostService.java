package com.family.hwang.service;

import com.family.hwang.controller.request.post.PostCommentRequest;
import com.family.hwang.controller.request.post.PostCreateRequest;
import com.family.hwang.controller.request.post.PostModifyRequest;
import com.family.hwang.controller.request.post.PostSearch;
import com.family.hwang.excecption.HwangFamilyRuntimeException;
import com.family.hwang.model.Comment;
import com.family.hwang.model.Post;
import com.family.hwang.model.entity.CommentEntity;
import com.family.hwang.model.entity.LikeEntity;
import com.family.hwang.model.entity.PostEntity;
import com.family.hwang.model.entity.UserEntity;
import com.family.hwang.repository.CommentEntityRepository;
import com.family.hwang.repository.LikeEntityRepository;
import com.family.hwang.repository.PostEntityRepository;
import com.family.hwang.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.family.hwang.excecption.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;
    private final CommentEntityRepository commentEntityRepository;

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
            throw new HwangFamilyRuntimeException(INVALID_PERMISSION, String.format("user %s has no permission with post %d", userName, postId));
        }

        postEntity.edit(request.getTitle(), request.getBody());
        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, Long postId) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        if (postEntity.getUser() != userEntity) {
            throw new HwangFamilyRuntimeException(INVALID_PERMISSION, String.format("user %s has no permission with post %d", userName, postId));
        }

        likeEntityRepository.deleteAllByPost(postEntity);
        commentEntityRepository.deleteAllByPost(postEntity);
        postEntityRepository.delete(postEntity);
    }

    public List<Post> list(PostSearch postSearch) {
        return postEntityRepository.getList(postSearch).stream()
                .map(Post::fromEntity)
                .collect(Collectors.toList());

    }

    public List<Post> my(String userName, PostSearch postSearch) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        return postEntityRepository.getListByUserName(userEntity, postSearch).stream()
                .map(Post::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void like(Long postId, String userName) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        likeEntityRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
            throw new HwangFamilyRuntimeException(ALREADY_LIKED_POST, String.format("userName %s already like the post %s", userName, postId));
        });

        var entity = LikeEntity.builder()
                .user(userEntity)
                .post(postEntity)
                .build();

        likeEntityRepository.save(entity);
    }

    public Long likeCount(Long postId) {
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        return likeEntityRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Long postId, PostCommentRequest request, String userName) {
        UserEntity userEntity = getUserEntityOrExceptions(userName);
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        CommentEntity entity = CommentEntity.builder()
                .user(userEntity)
                .post(postEntity)
                .comment(request.getComment())
                .build();

        commentEntityRepository.save(entity);
    }

    public Page<Comment> getComment(Long postId, Pageable pageable) {
        PostEntity postEntity = getPostEntityOrExceptions(postId);

        return commentEntityRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }

    /**
     * Whether user exist or not
     *
     * @param userName
     * @return UserEntity
     */

    private UserEntity getUserEntityOrExceptions(String userName) {
        return userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new HwangFamilyRuntimeException(USER_NOT_FOUND, String.format("%s not founded", userName)));
    }

    /**
     * Whether post exist or not
     *
     * @param postId
     * @return PostEntity
     */

    private PostEntity getPostEntityOrExceptions(Long postId) {
        return postEntityRepository.findById(postId).orElseThrow(() ->
                new HwangFamilyRuntimeException(POST_NOT_FOUND, String.format("%s not founded", postId)));
    }
}
