package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시글 관련 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 게시글의 생성, 조회, 수정, 삭제(CRUD) 및 정렬 조회 기능을 제공합니다.
 *
 * @author 김민호
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    /**
     * <새로운 게시글을 생성 컨트롤러>
     *
     * @param request 게시글 생성에 필요한 정보(제목, 내용 등)를 담은 DTO
     * @return 생성된 게시글 정보와 상태 코드 201(Created)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 생성 API",
            description = "프론트야 알제?")
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request){
        PostResponse response = postService.creatPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * <게시글 전체 조회 컨트롤러>
     *
     * @return 전체 게시글 리스트와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 전체 조회 API",
            description = "프론트야 알제?")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        List<PostResponse> responses = postService.getAllPosts2();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    /**
     * <특정 ID를 가진 게시글의 상세 정보를 조회 컨트롤러>
     *
     * @param postId
     * @return 해당 게시글 정보와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "특정 게시글 조회 API",
            description = "프론트야 알제?")
    @GetMapping("/posts/{post-id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("post-id") Long postId){
        PostResponse response = postService.getPostById(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /**
     * <최신 등록순으로 게시글 목록을 조회 컨틀롤러>
     *
     * @return 최신순으로 정렬된 게시글 리스트와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 최신순 조회 API",
            description = "프론트야 알제?")
    @GetMapping("/posts/latest")
    public ResponseEntity<List<PostResponse>> getLatestPosts(){
        List<PostResponse> responses = postService.getLatestPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    /**
     * <조회수가 높은 순(인기순)으로 게시글 목록을 조회 컨트롤러>
     *
     * @return 조회수 순으로 정렬된 게시글 리스트와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 조회수 많은순 조회 API",
            description = "프론트야 알제?")
    @GetMapping("/posts/best")
    public ResponseEntity<List<PostResponse>> getBestPosts(){
        List<PostResponse> responses = postService.getBestPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    /**
     * <게시글 수정 컨트롤러>
     *
     * @param postId
     * @param request
     * @return 수정된 게시글 정보와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 수정 API",
            description = "프론트야 알제?")
    @PutMapping("/posts/{post-id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("post-id") Long postId, @RequestBody UpdatePostRequest request){
        PostResponse response = postService.updatePost(postId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /**
     * <특정 ID의 게시글을 삭제 컨트롤러>
     *
     * @param postId
     * @return 삭제 성공 여부(Boolean)와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 삭제 API",
            description = "프론트야 알제?")
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable("post-id") Long postId){
        Boolean response = postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}