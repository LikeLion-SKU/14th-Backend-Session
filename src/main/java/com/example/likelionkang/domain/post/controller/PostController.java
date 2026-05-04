package com.example.likelionkang.domain.post.controller;


import com.example.likelionkang.domain.post.dto.request.CreatePostRequest;
import com.example.likelionkang.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionkang.domain.post.dto.response.PostResponse;
import com.example.likelionkang.domain.post.entity.Post;
import com.example.likelionkang.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Posts", description = "게시글 관련 API")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성",
    description = "요청으로 전달된 게시글 정보로 새로운 게시글을 생성하는 API")

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    @Operation(summary = "게시글 전체 조회",
               description = "모든 게시글 목록을 조회하는 API")

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam(defaultValue = "latest") String sortBy) {
        // 서비스의 getAllPosts 메서드에 정렬 기준을 전달합니다.
        List<PostResponse> responses = postService.getAllPosts(sortBy);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }
    @Operation(summary = "게시글 단건 조회",
               description = "게시글 ID로 특정 게시글을 조회하는 API")
    @GetMapping("/posts/{post-id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("post-id") Long postId) {
        PostResponse response = postService.getPostById(postId);
        return ResponseEntity
                .status((HttpStatus.OK))
                .body(response);
    }
    @Operation(summary = "게시글 수정",
    description = "게시글 수정하는 API")
    @PutMapping("/posts/{post-id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("post-id") Long postId, @RequestBody UpdatePostRequest request) {
        PostResponse response = postService.updatePost(postId,request);
        return ResponseEntity
                .status((HttpStatus.OK))
                .body(response);
    }
    @Operation(summary = "게시글 삭제",
    description = "게시글을 삭제하는 API")
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<Boolean> deletePost(
            @PathVariable("post-id") Long postId) {
        Boolean response = postService.deletePost(postId);
        return ResponseEntity
                .status((HttpStatus.OK))
                .body(response);
    }


}
