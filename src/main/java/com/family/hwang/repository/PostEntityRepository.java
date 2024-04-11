package com.family.hwang.repository;

import com.family.hwang.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {

}
