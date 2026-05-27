package com.likelion.besession.domain.post.service;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.repository.PostRepository;
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
    public PostResponse createPost(CreatePostRequest createPostRequest) {
        log.info("[PostService] 게시글 생성 요청 - title: {}", createPostRequest.getTitle());

        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        log.info("[PostService] 게시글 생성 완료 - postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());
        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1() {
        List<Post> posts = postRepository.findAll();
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", posts.size());
        return posts.stream().map(this::toPostResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(toPostResponse(post));
        }
        log.debug("[PostService] 게시글 전체 조회 완료 - 총 {}건", postResponses.size());
        return postResponses;
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        log.debug("[PostService] 게시글 단건 조회 - postId: {}", postId);
        Post post1 = postRepository.findById(postId).orElseThrow(() -> {
            log.warn("[PostService] 게시글을 찾을 수 없습니다 - postId: {}", postId);
            return new IllegalArgumentException("Post not found");
        });

        Post post2 = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        log.debug("[PostService] post1 == post2: {}", (post1 == post2));

        return toPostResponse(post1);
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        log.info("[PostService] 게시글 수정 요청 - postId: {}", postId);

        // 게시글 찾는 쿼리문 (1)
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.warn("[PostService] 게시글을 찾을 수 없습니다 - postId: {}", postId);
            return new IllegalArgumentException("Post not found");
        });

        // 게시글 수정 쿼리문..? (2)
        post.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());

        // 게시글 수정 내용 적용 쿼리문 (3)
        Post savedPost = postRepository.save(post);

        log.info("[PostService] 게시글 수정 완료 - postId: {}, title: {}", savedPost.getId(), savedPost.getTitle());
        return toPostResponse(savedPost);
    }

    @Transactional
    public Boolean deletePost(Long postId) {
        log.info("[PostService] 게시글 삭제 요청 - postId: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.warn("[PostService] 게시글을 찾을 수 없습니다 - postId: {}", postId);
            return new IllegalArgumentException("Post not found");
        });
        postRepository.delete(post); // 1차 캐시에서 삭제, SQL 쓰기 지연 저장소에 delete 쿼리 저장
        log.info("[PostService] 게시글 삭제 완료 - postId: {}", postId);
        return true;
    }

    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder().postId(post.getId()).title(post.getTitle()).content(post.getContent()).build();
    }
}
