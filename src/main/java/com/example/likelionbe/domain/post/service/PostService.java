package com.example.likelionbe.domain.post.service;

import com.example.likelionbe.domain.post.dto.request.CreatePostRequest;
import com.example.likelionbe.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionbe.domain.post.dto.response.PostResponse;
import com.example.likelionbe.domain.post.entity.Post;
import com.example.likelionbe.domain.post.entity.PostFilter;
import com.example.likelionbe.domain.post.exception.PostErrorCode;
import com.example.likelionbe.domain.post.repository.PostRepository;
import com.example.likelionbe.global.exception.CustomException;
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
    public PostResponse createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .build();
        post = postRepository.save(post);
        log.debug("[PostService] 게시글 생성 완료 - postId: {}", post.getId());
        return toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(UpdatePostRequest request) {
        Post post = postRepository.findById(request.postId()).orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.updatePost(request.title(), request.content());
        log.debug("[PostService] 게시글 수정 완료 - postId: {}", post.getId());
        return toPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostList(PostFilter filter) {
        List<Post> posts;
        if(filter==null) {
            posts = postRepository.findAll();
            log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", posts.size());
            return posts.stream().map(this::toPostResponse).toList();
        }

        if(filter.equals(PostFilter.LATEST)){
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findAllByOrderByViewCountDesc();
        }
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건, 조회 필터 기준: {}", posts.size(), filter.name());
        return posts.stream().map(this::toPostResponse).toList();
    }

    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.viewPost();
        log.debug("[PostService] 게시글 단건 조회 완료 - postId: {}", postId);
        return toPostResponse(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
        log.debug("[PostService] 게시글 삭제 완료 postId: {}", postId);
    }


    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .build();
    }
}
