package com.example.besession.domain.post.respository;

import com.example.besession.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    // 최신순
    List<Post> findAllByOrderByIdDesc();
    // 조회순
    List<Post> findAllByOrderByViewCountDesc();
}
