package com.likjelion.besession.domain.post.controller;

import com.likjelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likjelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likjelion.besession.domain.post.dto.response.PostResponse;
import com.likjelion.besession.domain.post.entity.Post;
import com.likjelion.besession.domain.post.service.PostService;
import com.likjelion.besession.global.common.BaseResponse;
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
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "게시글 생성",
            description = "요청으로 전달된 게시글 정보로 새로운 게시물을 생성하는 API"
    )
    @PostMapping("/posts")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(
            @Valid @RequestBody CreatePostRequest request)
    {

        PostResponse response = postService.createPost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201,"게시글 생성에 성공했습니다.", response));
    }

    @Operation(
            summary = "게시글 전체 조회",
            description = "모든 게시글 목록을 조회하는 API"
    )
    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {

        List<PostResponse> posts = postService.getAllPosts2();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(posts));
    }


    @Operation(
            summary = "게시글 최근 순 or 조회수 순 조회",
            description = "쿼리파라미터에 따라 최신 순 or 조회수 순으로 게시글 조회"
    )
    // 최근 순 or 조회수 순 조회
    @GetMapping("/api/posts")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getPostByViewOrRecent(
            @RequestParam(defaultValue = "views") String condition
    ) {
        //최신순 조회
        if(condition.equals("latest")) {
            List<PostResponse> latestPostList = postService.findAllByOrderByUpdatedAt();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(BaseResponse.success(latestPostList));
        }
        //조회수 순 조회
        List<PostResponse> viewPostList = postService.findAllByOrderByView();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(viewPostList));

    }

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID로 게시글을 조회하는 API"
    )
    @GetMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable("post-id") Long postId) {

        PostResponse post = postService.getPostById(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(post));
    }


    @Operation(
            summary = "게시글 수정",
            description = "게시글 ID와 게시물 정보로 게시글을 수정하는 API"
    )
    @PutMapping("posts/{post-id}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(@PathVariable("post-id") Long postId,
                                             @Valid @RequestBody UpdatePostRequest request) {

        PostResponse postResponse = postService.updatePost(postId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(postResponse));
    }

    @Operation(
            summary = "게시글 삭제 조회",
            description = "게시글 ID로 게시글을 삭제하는 API"
    )
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<Boolean>> deletePostById(@PathVariable("post-id") Long postId) {

        Boolean result = postService.deletePost(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(result));
    }
}
