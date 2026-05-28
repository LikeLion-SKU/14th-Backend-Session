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
    public PostResponse creatPost(CreatePostRequest request){

        // 게시글 생성 로그
        log.info("[PostService] 게시글 생성 요청 title: {}", request.getTitle());

        // 1. DTO로부터 게시글 객체 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 2. DB에 저장
        Post savedPost = postRepository.save(post);

        log.info("[PostService] 게시글 생성 완료 postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());
        // 3. PostResponse 형태로 만들어서 반환
        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1(){
        // 1. List<PostResponse> 객체를 미리 생성
        List<PostResponse> postResponses = new ArrayList<>();

        // 2. DB에서 Post 목록을 불러오기
        List<Post> postList = postRepository.findAll();

        // 3. Post 목록을 PostResponse에 맞게 변환해서 반환
        for(Post post : postList){
            postResponses.add(toPostResponse(post));
        }

        // 게시글 전체 조회 로그
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", postList.size());
        return  postResponses;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2(){
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    // 조회수 증가(상태 변경)가 일어나므로 readOnly = true는 제외하되, 예외 처리를 교안 구조로 변경합니다.
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        log.debug("[PostService] 게시글 단건 조회 postId: {}", postId);

        Post post1 = postRepository.findById(postId).orElseThrow(() -> {
            // 예외 발생 직전 상황 및 식별값 기록
            log.warn("[PostService] 게시글을 찾을 수 없습니다 postId: {}", postId);
            return new IllegalArgumentException("Post not found");
        });

        Post post2 = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("Post not found")
        );

        log.debug("[PostService] post1 == post2: {}", (post1 == post2));

        return toPostResponse(post1);
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
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {

        // 게시글 수정 요청 로그
        log.info("[PostService] 게시글 수정 요청 postId: {}", postId);

        // 게시글 찾는 쿼리문 (1)
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            // 게시글 찾을수 없음 Warn
            log.warn("[PostService] 게시글을 찾을 수 없습니다 - postId: {}", postId);
            return new IllegalArgumentException("Post not found");
        });

        // 게시글 수정 쿼리문 (2)
        post.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());

        // 게시글 수정 내용 적용 쿼리문 (3)
        Post savedPost = postRepository.save(post);

        // 게시글 수정 완료 Info
        log.info("[PostService] 게시글 수정 완료 postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());

        return toPostResponse(savedPost);
    }

    @Transactional
    public Boolean deletePost(Long postId){
        // 교안 실습 내용에 맞춰, 존재하지 않는 postId를 삭제하려 할 때 예외를 발생시키도록 조회 단계를 추가합니다.
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(PostErrorCode.POST_NOT_FOUND)); //

        // 1. 불러온 엔티티 객체를 이용해 DB에서 삭제하기
        postRepository.delete(post); //
        return true;
    }


    private PostResponse toPostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViewCount())
                .build();
    }
}