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

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest request){
        // log.info("[PostService] 게시글 생성 요청 - title: {}", createPostRequest.getTitle());
        // 1. DTO로부터 게시글 객체 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 2. DB에 저장
        Post savedPost = postRepository.save(post);

        log.info("[PostService] 게시글 생성 완료 - postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());
        // 3. PostResponse 형태로 만들어서 반환
        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1(){
        List<Post> postList = postRepository.findAll();
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", postList.size());
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2(){
        List<Post> postList = postRepository.findAll();
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", postList.size());
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    @Transactional
    public PostResponse getPostById(Long postId){
        log.debug("[PostService] 게시글 단건 조회 - postId: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(()->
                new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();

        return toPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getLatestPosts(){
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getBestPosts(){
        List<Post> postList = postRepository.findAllByOrderByViewCountDesc();
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request){
        log.info("[PostService] 게시글 수정 요청 - postId: {}", postId);
        // 1. 수정할 게시글 객체를 DB에서 불러옴
        Post post = postRepository.findById(postId).orElseThrow(()->
                new CustomException(PostErrorCode.POST_NOT_FOUND));
        // 2. 수정할 내용으로 바꾸기
        post.updatePost(request);
        // ?. DB에 수정한 내용 저장
        postRepository.save(post);
        // 3. PostResponse 형태로 변환해서 반환하기
        // log.info("[PostService] 게시글 수정 완료 - postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());
        return toPostResponse(post);
    }

    @Transactional
    public Boolean deletePost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
;        // 1. postId로 DB에 존재하는 객체 삭제하기
        postRepository.deleteById(postId);
        return true;
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
