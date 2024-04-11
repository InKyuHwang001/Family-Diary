package com.family.hwang.service;

import com.family.hwang.controller.request.PostCreateRequest;
import com.family.hwang.excecption.ErrorCode;
import com.family.hwang.excecption.HwangFamilyException;
import com.family.hwang.model.entity.PostEntity;
import com.family.hwang.model.entity.UserEntity;
import com.family.hwang.repository.PostEntityRepository;
import com.family.hwang.repository.UserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;


    @Transactional
    public void create(PostCreateRequest request, String userName) {
        //find user
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new HwangFamilyException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        //post save
        postEntityRepository.save(new PostEntity());

    }


}
