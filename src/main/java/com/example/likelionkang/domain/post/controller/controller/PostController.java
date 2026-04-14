package com.example.likelionkang.domain.post.controller.controller;


import com.example.likelionkang.domain.post.dto.request.CreatePostRequest;
import com.example.likelionkang.domain.post.dto.request.UpdatePostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Posts", description = "게시글 관련 API")
public class PostController {

    @Operation(summary = "게시글 생성",
    description = "요청으로 전달된 게시글 정보로 새로운 게시글을 생성하는 API")

    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("게시글 생성");

    }

    @Operation(summary = "게시글 전체 조회",
               description = "모든 게시글 목록을 조회하는 API")

    @GetMapping("/posts")
    public ResponseEntity<String> getAllPosts() {

        return ResponseEntity
                .status((HttpStatus.OK))
                .body("게시글 전체 조회");
    }
    @Operation(summary = "게시글 단건 조회",
               description = "게시글 ID로 특정 게시글을 조회하는 API")
    @GetMapping("/posts/{post-id}")
    public ResponseEntity<String> getPostById(@PathVariable("post-id") Long postId) {

        return ResponseEntity
                .status((HttpStatus.OK))
                .body(postId + "번 게시글 조회");
    }
    @Operation(summary = "게시글 수정",
    description = "게시글 수정하는 API")
    @PutMapping("/posts/{post-id}")
    public ResponseEntity<String> updatePost(
            @PathVariable("post-id") Long postId, @RequestBody UpdatePostRequest request) {

        return ResponseEntity
                .status((HttpStatus.OK))
                .body(postId + "빈 게시글 수정");
    }
    @Operation(summary = "게시글 삭제",
    description = "게시글을 삭제하는 API")
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<String> deletePost(
            @PathVariable("post-id") Long postId) {

        return ResponseEntity
                .status((HttpStatus.OK))
                .body(postId + "빈 게시글 삭제");
    }


}
