package com.likjelion.besession.domain.post.service;

import com.likjelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likjelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likjelion.besession.domain.post.dto.response.PostResponse;
import com.likjelion.besession.domain.post.entity.Post;
import com.likjelion.besession.domain.post.exception.PostErrorCode;
import com.likjelion.besession.domain.post.repository.PostRepository;
import com.likjelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {



    private final PostRepository postRepository;

    //게시글 저장
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        log.info("[PostController] 게시물 생성");
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        log.info("[PostController] 게시물 생성 완료 - postId: {}", savedPost.getId());
        return toPostResponse(savedPost);
    }

    //게시글 전체 조회
    public List<PostResponse> getAllPosts1() {
        List<Post> allRealPosts = postRepository.findAll();
            log.debug("[PostService] 게시글 조회 요청 - 총: {}건", allRealPosts.size());
        List<PostResponse> allPosts = new ArrayList<>();

        for (Post post : allRealPosts) {
            allPosts.add(toPostResponse(post));
        }

        log.debug("[PostService] 게시글 조회 완료 - 총: {}건", allRealPosts.size());
        return allPosts;
    }

    //게시물 전체 조회 (stream 활용)
    public List<PostResponse> getAllPosts2() {

        List<Post> allRealPosts = postRepository.findAll();


        List<PostResponse> allPosts = allRealPosts.stream().map(a -> toPostResponse(a)).toList();


        return allPosts;
    }

    //게시글 단건 조회
    public PostResponse getPostById(Long postId) {

        log.debug("[PostController] 게시물 단건 조회 - postID: {}", postId);
        // findById 같은 경우 해당 게시물이 없을경우 에러가 뜨기때문에 에러 처리를 해줘야 반환값이 Post로 나옴 안그럼 Optional로 나옴
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        {log.warn("[PostService] 게시글을 찾을 수없습니다, - postId: {}", postId);
                        return new CustomException(PostErrorCode.POST_NOT_FOUND);});

        log.debug("[PostController] 게시물 단건 조회 완료 - postID: {}", postId);
        return toPostResponse(post);

    }

    //게시글 수정
    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {

        log.info("[PostController] 게시글 수정 요청 - postId: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        {log.warn("[PostService] 게시글 수정 오류 - postId: {}", postId);
                            return new CustomException(PostErrorCode.POST_NOT_FOUND);});
        Post updatedPost = post.updatePost(request);

        log.info("[PostController] 게시글 수정 완료 - postId: {}", postId);
        return toPostResponse(updatedPost);
    }

    //게시글 삭제
    @Transactional
    public Boolean deletePost(Long postId) {
        log.info("[PostController] 게시글 삭제 요청 - postId: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.warn("[PostService] 게시글 삭제 오류 - postId: {}", postId);
                    return new CustomException(PostErrorCode.POST_NOT_FOUND);});
        postRepository.delete(post);

        log.info("[PostController] 게시글 삭제 완료 - postId: {}", postId);

        return true;

    }

    public List<PostResponse> findAllByOrderByUpdatedAt() {
        // 게시물이 없을 경우 오류
        if(postRepository.findAll().isEmpty()) {
            throw new IllegalStateException("게시물이 없습니다.");
        }

        // 게시물이 존재한다면 조회
        List<Post> latestPostList  = postRepository.findAllByOrderByUpdatedAtDesc();

        //dto 변환
        List<PostResponse> findAllPostByUpdatedAt = latestPostList.stream()
                .map(post -> toPostResponse(post)).toList();
        return findAllPostByUpdatedAt;
    }

    public List<PostResponse> findAllByOrderByView() {
        // 게시물이 없을 경우 오류
        if(postRepository.findAll().isEmpty()) {
            throw new IllegalStateException("게시물이 없습니다.");
        }
        // 게시물이 조회수 순 존재한다면 조회
        List<Post> findAllPostByView = postRepository.findAllByOrderByViewDesc();

        //dto변환
        List<PostResponse> findAllPostByUpdatedAt = findAllPostByView.stream()
                .map(post -> toPostResponse(post)).toList();

        return findAllPostByUpdatedAt;
    }

    // 게시물 -> dto 변환 매서드
    private static PostResponse toPostResponse(Post a) {
        return PostResponse.builder()
                .postId(a.getId())
                .title(a.getTitle())
                .content(a.getContent())
                .build();
    }





}
