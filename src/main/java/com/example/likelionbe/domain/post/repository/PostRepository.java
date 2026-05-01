package com.example.likelionbe.domain.post.repository;

import com.example.likelionbe.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByOrderByViewCountDesc();
}
