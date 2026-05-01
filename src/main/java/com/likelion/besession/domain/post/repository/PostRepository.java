package com.likelion.besession.domain.post.repository;

import com.likelion.besession.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 최신순 조회
    List<Post> findAllByOrderByCreatedAtDesc();

    // 조회수 많은것부터 조회
    List<Post> findAllByOrderByViewDesc();
}
