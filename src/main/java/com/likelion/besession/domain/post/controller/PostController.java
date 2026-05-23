package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.PostSortType;
import com.likelion.besession.domain.post.service.PostService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @Operation(summary = "게시글 작성", description = "요청 받은 바디에 따라 게시글을 작성하는 API")
    @PostMapping("/posts")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request) {

        PostResponse postResponse = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "게시글 작성에 성공했습니다.", postResponse));
    }

    // 모든 게시글 조회
    @GetMapping("/posts")
    @Operation(summary = "모든 게시글 정렬 조회", description = "모든 게시글을 요청받은 정렬 기준으로 조회하는 API")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(
            @Parameter(description = "정렬 기준 - createdDate, viewCount, id(default)", example = "viewCount")
            @RequestParam(required = false, defaultValue = "id", value = "sortType") PostSortType sortType) {

        // 반환할 결과를 저장해둘 리스트 선언
        List<PostResponse> postResponses;

        // 서비스 메소드 하나 만들어서, 매개변수로 sortType 전달해주는 메소드 호출
        postResponses = postService.getAllPostsWithSortType(sortType);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(postResponses)); // 데이터만 넣으면 200이 되도록 이미 코드를 짜놨음 -> 그냥 데이터만 담아서 보내기
    }

    // ID 기반 단일 게시글 조회
    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 단건 조회", description = "게시글 ID 기반 조회 API")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable Long postId){

        PostResponse postResponse = postService.getPostById(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(postResponse));
    }

    // ID 기반 게시글 수정
    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정", description = "게시글 ID 기반 수정 API")
    public ResponseEntity<BaseResponse<PostResponse>> putPostById(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest request) {

        PostResponse postResponse = postService.updatePost(postId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(postResponse));
    }

    // ID 기반 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제", description = "게시글 ID 기반 삭제 API")
    public ResponseEntity<BaseResponse<Boolean>> deletePostById(@PathVariable Long postId){

        boolean isDeleted = postService.deletePost(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(isDeleted));
    }
}
