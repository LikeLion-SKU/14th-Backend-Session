package com.likelion.besession.domain.post.repository;

import com.likelion.besession.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // ID 순
    List<Post> findAllByOrderByIdDesc();
    // viewCount순
    List<Post> findAllByOrderByViewCountDesc();
}
