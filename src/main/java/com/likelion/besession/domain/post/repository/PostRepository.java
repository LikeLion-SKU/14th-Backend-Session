package com.likelion.besession.domain.post.repository;

import com.likelion.besession.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //public List<Post> findAllOrderByViewCountDesc();

    List<Post> findAllByOrderByViewCountDesc();

    List<Post> findAllByOrderByCreatedDateDesc();
}
