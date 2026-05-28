package com.likelion.besession.domain.post.repository;

import com.likelion.besession.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();

    // Views 대신 엔티티 필드명인 ViewCount를 사용합니다.
    List<Post> findAllByOrderByViewCountDesc();

}