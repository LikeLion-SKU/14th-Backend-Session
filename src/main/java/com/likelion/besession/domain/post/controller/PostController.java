package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

  private final PostService postService;

  @Operation(summary = "게시글 생성",
             description = "요청으로 전달된 게시글 정보로 새로운 게시글을 생성하는 API")
  @PostMapping("/posts")
  public ResponseEntity<PostResponse> createPost(
          @Parameter(description = "게시글 작성 내용") @RequestBody CreatePostRequest request) {
    PostResponse postResponse = postService.createPost(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(postResponse);
  }

  @Operation(summary = "게시글 전체 조회",
             description = "모든 게시글 목록을 조회하는 API")
  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    List<PostResponse> postResponseList = postService.getAllPosts1();

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(postResponseList);
  }

  @Operation(summary = "게시글 단건 조회",
             description = "게시글 ID로 특정 게시글을 조회하는 API")
  @GetMapping("/posts/{post-id}")
  public ResponseEntity<PostResponse> getPostById(
          @Parameter(description = "특정 게시글 ID") @PathVariable("post-id") Long postId) {
    PostResponse postResponse = postService.getPostById(postId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(postResponse);
  }

  @Operation(summary = "게시글 수정",
             description = "게시글 ID와 요청으로 전달된 게시글 정보로 게시글을 수정하는 API")
  @PutMapping("/posts/{post-id}")
  public ResponseEntity<PostResponse> updatePost(
          @Parameter(description = "특정 게시글 ID") @PathVariable("post-id") Long postId,
          @Parameter(description = "게시글 수정 내용") @RequestBody UpdatePostRequest request) {
    PostResponse postResponse = postService.updatePost(postId, request);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(postResponse);
  }

  @Operation(summary = "게시글 삭제",
             description = "게시글 ID로 특정 게시글을 삭제하는 API")
  @DeleteMapping("/posts/{post-id}")
  public ResponseEntity<Boolean> deletePost(
          @Parameter(description = "특정 게시글 ID") @PathVariable("post-id") Long postId) {
    Boolean result = postService.deletePost(postId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(result);
  }
}
