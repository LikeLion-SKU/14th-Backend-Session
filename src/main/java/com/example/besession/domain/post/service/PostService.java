package com.example.besession.domain.post.service;

import com.example.besession.domain.post.dto.request.CreatePostRequest;
import com.example.besession.domain.post.dto.request.UpdatePostRequest;
import com.example.besession.domain.post.dto.response.PostResponse;
import com.example.besession.domain.post.entity.Post;
import com.example.besession.domain.post.exception.PostErrorCode;
import com.example.besession.domain.post.respository.PostRepository;
import com.example.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    // 게시글 생성
    public PostResponse createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);

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

        return postRepository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toPostResponse)
                .toList();
    }

    @Transactional
    // 게시글 단일 조회
    public PostResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        post.increaseViewCount();

        return toPostResponse(post);
    }

    @Transactional
    // 게시글 수정
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        post.updatePost(request);

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