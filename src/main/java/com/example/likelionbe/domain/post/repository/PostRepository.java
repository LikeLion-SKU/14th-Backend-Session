package com.example.likelionbe.domain.post.repository;

import com.example.likelionbe.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
