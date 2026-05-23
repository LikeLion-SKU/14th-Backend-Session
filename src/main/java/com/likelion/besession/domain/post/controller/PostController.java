package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.entity.PostFilter;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.request.CreatePostRequest;

import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.service.PostService;
import com.likelion.besession.global.common.BaseResponse;
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
@Tag(name = "Post", description = "게시글 관련 API") // api 그룹화 및 설명
public class PostController {

    private final PostService postService;

    // 개별 api의 엔드포인트의 목적과 동작 설명
    @Operation(summary = "게시글 생성", description = "요청으로 전달된 게시글 정보로 새로운 게시글을 생성하는 API")

    @PostMapping("/posts")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request){
        PostResponse response = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "게시글 생성에 성공했습니다", response));
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회하는 API")

    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostList(@Valid @RequestParam PostFilter filter){
        return ResponseEntity.ok(BaseResponse.success(postService.getAllposts2(filter)));
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 ID로 특정 게시글을 조회하는 API")

    @GetMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@Valid @PathVariable("post-id") Long postId) {
        return ResponseEntity.ok(BaseResponse.success(postService.getPostById(postId)));
    }

    @Operation(summary = "게시글 수정", description = "게시글 ID와 요청으로 전달된 게시글 정보로 게시글을 수정하는 API")

    @PutMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            @Valid @PathVariable("post-id") Long postId, @RequestBody UpdatePostRequest request){
        return ResponseEntity.ok(BaseResponse.success(postService.updatePost(postId, request)));
    }

    @Operation(summary = "게시글 제거", description = "게시글 ID로 특정 게시글을 삭제하는 API")
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<String>> deletePost(
            @PathVariable("post-id") Long postId // 💡 오타 수정: @Vaild -> @Valid (단, Long 자체는 검증할 게 없어 생략 가능)
    ) {
        postService.deletePost(postId);

        // 문자열을 BaseResponse.success()로 감싸서 리턴!
        return ResponseEntity.ok(BaseResponse.success("게시물 삭제 성공"));
    }
}
