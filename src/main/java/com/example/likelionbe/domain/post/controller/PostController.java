package com.example.likelionbe.domain.post.controller;

import com.example.likelionbe.domain.post.dto.request.CreatePostRequest;
import com.example.likelionbe.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionbe.domain.post.dto.response.PostResponse;
import com.example.likelionbe.domain.post.entity.Post;
import com.example.likelionbe.domain.post.entity.PostFilter;
import com.example.likelionbe.domain.post.service.PostService;
import com.example.likelionbe.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@RequestBody @Valid CreatePostRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "게시글 생성에 성공했습니다.", postService.createPost(request)));
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정 API 입니다.")
    @PutMapping("/posts")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(@RequestBody @Valid UpdatePostRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("게시글 수정에 성공했습니다.", postService.updatePost(request)));
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 API 입니다.")
    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostList(@RequestParam PostFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("게시글 목록 조회에 성공했습니다.", postService.getPostList(filter)));
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 단건 조회 API 입니다.")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(@PathVariable Long postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("게시글 단건 조회에 성공했습니다.", postService.getPost(postId)));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API 입니다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("게시물 삭제에 성공했습니다.", null));
    }
}
