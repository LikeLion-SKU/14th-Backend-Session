package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {

  @PostMapping("/posts")
  public ResponseEntity<String> createPost(@RequestBody CreatePostRequest request) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("게시글 생성");
  }

  @GetMapping("/posts")
  public ResponseEntity<String> getAllPosts() {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body("게시글 전체 조회");
  }

  @GetMapping("/posts/{post-id}")
  public ResponseEntity<String> getPostById(@PathVariable("post-id") Long postId) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(postId + "번 게시글 조회");
  }

  @PutMapping("/posts/{post-id}")
  public ResponseEntity<String> updatePost(
      @PathVariable("post-id") Long postId, @RequestBody UpdatePostRequest request) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(postId + "번 게시글 수정");
  }

  @DeleteMapping("/posts/{post-id}")
  public ResponseEntity<String> deletePost(@PathVariable("post-id") Long postId) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(postId + "번 게시글 삭제");
  }
}
