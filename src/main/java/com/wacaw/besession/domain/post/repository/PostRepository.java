package com.wacaw.besession.domain.post.repository;

import com.wacaw.besession.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 최신순
  List<Post> findAllByOrderByCreatedAtDesc();

  // 조회수순
  List<Post> findAllByOrderByViewsDesc();

}
