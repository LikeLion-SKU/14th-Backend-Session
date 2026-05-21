package com.likelion.besession.domain.post.controller;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
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
    @Operation(summary = "게시글 생성 API", description = "프론트야 알제?")
    @PostMapping("/posts")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request){
        PostResponse response = postService.creatPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "게시글 생성에 성공했습니다.", response)); // [cite: 365]
    }

    /**
     * <게시글 전체 조회 컨트롤러>
     *
     * @return 전체 게시글 리스트와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 전체 조회 API", description = "프론트야 알제?")
    @GetMapping("/posts")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts(){
        List<PostResponse> responses = postService.getAllPosts2();
        return ResponseEntity
                .status(HttpStatus.OK)
                // 기존에 201 코드와 "생성 성공" 메시지로 잘못 들어가 있던 부분을 200 코드와 "조회 성공"으로 수정했습니다.
                .body(BaseResponse.success(200, "게시글 전체 조회에 성공했습니다.", responses));
    }

    /**
     * <특정 ID를 가진 게시글의 상세 정보를 조회 컨트롤러>
     *
     * @param postId
     * @return 해당 게시글 정보와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "특정 게시글 조회 API", description = "프론트야 알제?")
    @GetMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPostById(@PathVariable("post-id") Long postId){
        PostResponse response = postService.getPostById(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(response)); // [cite: 57]
    }

    /**
     * <최신 등록순으로 게시글 목록을 조회 컨트롤러>
     *
     * @return 최신순으로 정렬된 게시글 리스트와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 최신순 조회 API", description = "프론트야 알제?")
    @GetMapping("/posts/latest")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getLatestPosts(){
        List<PostResponse> responses = postService.getLatestPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                // BaseResponse.success()를 적용하여 응답 데이터 구조를 통일합니다. [cite: 35]
                .body(BaseResponse.success("최신순 게시글 조회에 성공했습니다.", responses)); // [cite: 59]
    }

    /**
     * <조회수가 높은 순(인기순)으로 게시글 목록을 조회 컨트롤러>
     *
     * @return 조회수 순으로 정렬된 게시글 리스트와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 조회수 많은순 조회 API", description = "프론트야 알제?")
    @GetMapping("/posts/best")
    public ResponseEntity<BaseResponse<List<PostResponse>>> getBestPosts(){
        List<PostResponse> responses = postService.getBestPosts();
        return ResponseEntity
                .status(HttpStatus.OK)
                // BaseResponse.success()를 적용하여 응답 데이터 구조를 통일합니다. [cite: 35]
                .body(BaseResponse.success("인기순 게시글 조회에 성공했습니다.", responses)); // [cite: 59]
    }

    /**
     * <게시글 수정 컨트롤러>
     *
     * @param postId
     * @param request
     * @return 수정된 게시글 정보와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 수정 API", description = "프론트야 알제?")
    @PutMapping("/posts/{post-id}")
    // 주간 실습 내용에 맞춰 수정 요청 본문(RequestBody)에도 @Valid 유효성 검사를 추가하는 것이 좋습니다.
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            @PathVariable("post-id") Long postId, @Valid @RequestBody UpdatePostRequest request){
        PostResponse response = postService.updatePost(postId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                // BaseResponse.success()를 적용하여 응답 데이터 구조를 통일합니다. [cite: 35]
                .body(BaseResponse.success("게시글 수정에 성공했습니다.", response)); // [cite: 59]
    }

    /**
     * <특정 ID의 게시글을 삭제 컨트롤러>
     *
     * @param postId
     * @return 삭제 성공 여부(Boolean)와 상태 코드 200(OK)을 포함한 ResponseEntity
     */
    @Operation(summary = "게시글 삭제 API", description = "프론트야 알제?")
    @DeleteMapping("/posts/{post-id}")
    public ResponseEntity<BaseResponse<Boolean>> deletePost(@PathVariable("post-id") Long postId){
        Boolean response = postService.deletePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                // BaseResponse.success()를 적용하여 응답 데이터 구조를 통일합니다. [cite: 35]
                .body(BaseResponse.success("게시글 삭제에 성공했습니다.", response)); // [cite: 59]
    }
}