package com.family.hwang.controller;

import com.family.hwang.controller.request.post.PostCreateRequest;
import com.family.hwang.controller.request.post.PostModifyRequest;
import com.family.hwang.controller.request.post.PostSearch;
import com.family.hwang.controller.response.PostResponse;
import com.family.hwang.controller.response.Response;
import com.family.hwang.model.Post;
import com.family.hwang.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request, authentication.getName());

        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post modify = postService.modify(postId, request, authentication.getName());

        return Response.success(PostResponse.fromPost(modify));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId, Authentication authentication){
        postService.delete(authentication.getName(), postId);

        return Response.success();
    }

    @GetMapping
    public Response<List<PostResponse>> list(PostSearch postSearch){
        return Response.success(postService.list(postSearch).stream()
                .map(PostResponse::fromPost)
                .collect(Collectors.toList()));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> my(PostSearch postSearch, Authentication authentication){
        return Response.success(postService.my(authentication.getName(), postSearch).map(PostResponse::fromPost));
    }


}
