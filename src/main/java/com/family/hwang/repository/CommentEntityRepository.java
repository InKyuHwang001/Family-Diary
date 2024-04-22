package com.family.hwang.repository;

import com.family.hwang.model.entity.CommentEntity;
import com.family.hwang.model.entity.PostEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long>, CommentEntityRepositoryCustom {

    Page<CommentEntity> findAllByPost(PostEntity postEntity, Pageable pageable);

    @Modifying
    @Transactional
    void deleteAllByPost(PostEntity post);

}
