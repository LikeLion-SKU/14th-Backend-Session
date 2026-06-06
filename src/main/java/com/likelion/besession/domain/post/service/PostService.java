package com.likelion.besession.domain.post.service;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.exception.PostErrorCode;
import com.likelion.besession.domain.post.repository.PostRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest request){
        log.info("[PostService] 게시글 생성 요청 - title: {}", request.getTitle());
        // 1. DTO로부터 게시글 객체 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .viewCount(0L)
                .build();

        // 2. DB에 저장
        Post savedPost = postRepository.save(post);

        log.info("[PostService] 게시글 생성 완료 - postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());

        // 3. PostResponse 형태로 만들어서 반환
        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1(){
        // DB에서 Post 목록을 불러오기
        List<Post> posts = postRepository.findAll();

        // 이미지 반영: 전체 조회 완료 디버그 로그 추가
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", posts.size());

        // 이미지 반영: 스트림 구조로 변환하여 반환
        return posts.stream().map(this::toPostResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2(){
        return postRepository.findAll()
                .stream().map(this::toPostResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId){
        // 이미지 반영: 단건 조회 요청 디버그 로그
        log.debug("[PostService] 게시글 단건 조회 - postId: {}", postId);

        // 이미지 반영: 동일 객체(1차 캐시) 비교 검증 로그를 위한 post1, post2 호출 구조 병합
        Post post1 = postRepository.findById(postId).orElseThrow(() -> {
            log.warn("[PostService] 게시글을 찾을 수 없습니다 - postId: {}", postId);
            return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });

        Post post2 = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(PostErrorCode.POST_NOT_FOUND)
        );

        // 이미지 반영: 영속성 컨텍스트 동일성 보장 확인 디버그 로그
        log.debug("[PostService] post1 == post2: {}", (post1 == post2));

        return toPostResponse(post1);
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request){
        log.info("[PostService] 게시글 수정요청 - postId: {}", postId);

        // 1. 수정할 게시글 객체를 DB에서 불러옴
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.warn("[PostService] 게시글을 찾을 수 없습니다 - postId: {}", postId);
            return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });

        // 2. 수정할 내용으로 바꾸기
        post.updatePost(request);

        // 3. DB에 수정한 내용 저장 (Dirty Checking이 작동하므로 생략 가능하나 명시 유지)
        postRepository.save(post);

        // 4. PostResponse 형태로 변환해서 반환하기 (오타 수정: Post -> post 객체 참조)
        log.info("[PostService] 게시글 수정 완료 - postId: {}, title: {}", post.getId(), post.getTitle());
        return toPostResponse(post);
    }

    @Transactional
    public Boolean deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        postRepository.delete(post);

        return true;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByLatest(){
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toPostResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByViewCount(){
        return postRepository.findAllByOrderByViewCountDesc()
                .stream()
                .map(this::toPostResponse)
                .toList();
    }

    private PostResponse toPostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .build();
    }
}