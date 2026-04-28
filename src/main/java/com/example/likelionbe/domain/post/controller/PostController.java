package com.example.likelionbe.domain.post.controller;

import com.example.likelionbe.domain.post.dto.request.CreatePostRequest;
import com.example.likelionbe.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionbe.domain.post.dto.response.PostResponse;
import com.example.likelionbe.domain.post.entity.Post;
import com.example.likelionbe.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "게시글 도메인 API")
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "게시글 생성 API 입니다.")
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정 API 입니다.")
    @PutMapping("/posts")
    public ResponseEntity<PostResponse> updatePost(@RequestBody @Valid UpdatePostRequest request) {
        return ResponseEntity.ok(postService.updatePost(request));
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 API 입니다.")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPostList() {
        return ResponseEntity.ok(postService.getPostList());
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 단건 조회 API 입니다.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API 입니다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("게시물 삭제 성공");
    }
}
