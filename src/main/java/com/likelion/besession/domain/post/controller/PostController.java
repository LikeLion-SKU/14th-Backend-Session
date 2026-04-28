package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    // 게시글 생성
    @Operation(summary = "게시글 작성", description = "요청 받은 바디에 따라 게시글을 작성하는 API")
    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("게시글 생성");
    }

    // 모든 게시글 조회
    @GetMapping("/posts")
    @Operation(summary = "모든 게시글 조회", description = "모든 게시글을 조회하는 API")
    public ResponseEntity<String> getAllPosts(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("모든 게시글 조회");
    }

    // ID 기반 단일 게시글 조회
    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 단건 조회", description = "게시글 ID 기반 조회 API")
    public ResponseEntity<String> getPostById(@PathVariable Long postId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postId + "번 게시글 조회 성공");
    }

    // ID 기반 게시글 수정
    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글 ID 기반 수정 API")
    public ResponseEntity<String> putPostById(@PathVariable Long postId, @RequestBody UpdatePostRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postId + "번 게시글 수정 성공");
    }

    // ID 기반 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글 ID 기반 삭제 API")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postId + "번 게시글 삭제 성공");
    }
}
