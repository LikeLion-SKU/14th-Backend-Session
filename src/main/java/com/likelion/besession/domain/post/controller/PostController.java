package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.entity.PostFilter;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.request.CreatePostRequest;

import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 관련 API") // api 그룹화 및 설명
public class PostController {

    private final PostService postService;

    // 개별 api의 엔드포인트의 목적과 동작 설명
    @Operation(summary = "게시글 생성", description = "요청으로 전달된 게시글 정보로 새로운 게시글을 생성하는 API")

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request){
        PostResponse response = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회하는 API")

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPostList(@RequestParam PostFilter filter){
        return ResponseEntity.ok(postService.getAllposts2(filter));
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 특정 게시글을 조회하는 API")

    @GetMapping("/posts/{post-id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("post-id") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @Operation(summary = "게시글 수정", description = "게시글 ID와 요청으로 전달된 게시글 정보로 게시글을 수정하는 API")

    @PutMapping("/posts/{post-id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("post-id") Long postId, @RequestBody UpdatePostRequest request){
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

    @Operation(summary = "게시글 제거", description = "게시글 ID로 특정 게시글을 삭제하는 API")
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<String> deletePost(@PathVariable("post-id") Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok("게시물 삭제 성공");
    }
}
