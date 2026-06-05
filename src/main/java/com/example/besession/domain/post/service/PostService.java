package com.example.besession.domain.post.service;

import com.example.besession.domain.post.dto.request.CreatePostRequest;
import com.example.besession.domain.post.dto.request.UpdatePostRequest;
import com.example.besession.domain.post.dto.response.PostResponse;
import com.example.besession.domain.post.entity.Post;
import com.example.besession.domain.post.exception.PostErrorCode;
import com.example.besession.domain.post.respository.PostRepository;
import com.example.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    // 게시글 생성
    public PostResponse createPost(CreatePostRequest request) {
        log.info("[PostService] 게시글 생성 요청 title={}", request.getTitle());
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        log.info("[PostService]게시글 생성 완료 - postid={},title={}", savedPost.getId(),savedPost.getTitle());
        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    // 게시글 전체 조회
    public List<PostResponse> getPosts(String sort) {
        if ("popular".equals(sort)) {
            return postRepository.findAllByOrderByViewCountDesc()
                    .stream()
                    .map(this::toPostResponse)
                    .toList();
        }

        log.info("[PostService] 게시글 전체 조회 완료- 총{}건");

        return postRepository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toPostResponse)
                .toList();
    }

    @Transactional
    // 게시글 단일 조회
    public PostResponse findById(Long postId) {
        log.debug("[PostService] 게시글 단일 조회 - postid={}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.warn("[PostService] 게시글을 찾을 수 없습니다. - postId={}", postId);
                    return new CustomException(PostErrorCode.POST_NOT_FOUND);
                });


        post.increaseViewCount();

        log.info("[PostService] 게시글 단일 조회 완료 - postid={}", postId);
        return toPostResponse(post);
    }

    @Transactional
    // 게시글 수정
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        log.info("[PostService] 게시글 수정 요청 - postId={}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.warn("[PostService] 게시글을 찾을 수 없습니다. - postId={}", postId);
                    return new CustomException(PostErrorCode.POST_NOT_FOUND);
                });

        post.updatePost(request);

        log.info("[PostService] 게시글 수정 완료 - postid={}", postId);
        return toPostResponse(post);
    }

    @Transactional
    // 게시글 삭제
    public Boolean deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        postRepository.delete(post);

        return true;
    }

    // 게시글 응답 변환
    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .build();
    }
}