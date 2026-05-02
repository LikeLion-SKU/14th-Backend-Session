package com.likjelion.besession.domain.post.repository;


import com.likjelion.besession.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {


    //최신 순 조회
    List<Post> findAllByOrderByUpdatedAtDesc();

    //조회수 순 조회
    List<Post> findAllByOrderByViewDesc();

}
