package com.likelion.besession.domain.post.repository;

import com.likelion.besession.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByOrderByCreatedAtDesc();

  List<Post> findAllByOrderByViewCountDesc();

}
