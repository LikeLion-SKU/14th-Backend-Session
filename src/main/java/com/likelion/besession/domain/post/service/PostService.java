package com.likelion.besession.domain.post.service;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest createPostRequest) {
        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::toPostResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            postResponses.add(toPostResponse(post));
        }
        return postResponses;
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        System.out.println("첫 게시글 찾기 전==========================");
        Post post1 = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        System.out.println("첫 게시글 찾기 후==========================");

        System.out.println("두 번째 게시글 찾기 전==========================");
        Post post2 = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        System.out.println("두 번째 게시글 찾기 후==========================");

        System.out.println("post 1 == post 2: " + (post1 == post2));

        return toPostResponse(post1);
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        System.out.println("게시글 찾기 전==========================");
        // 게시글 찾는 쿼리문 (1)
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        System.out.println("게시글 찾기 후==========================");

        System.out.println("게시글 수정 전========================");
        // 게시글 수정 쿼리문..? (2)
        post.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());
        System.out.println("게시글 수정 후=========================");

        System.out.println("게시글 수정 반영 전=========================");
        // 게시글 수정 내용 적용 쿼리문 (3)
        Post savedPost = postRepository.save(post);
        System.out.println("게시글 수정 반영 후========================");

        System.out.println("반환 직전========================");
        return toPostResponse(savedPost);
    }

    @Transactional
    public Boolean deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postRepository.delete(post); // 1차 캐시에서 삭제, SQL 쓰기 지연 저장소에 delete 쿼리 저장
        System.out.println("After delete========================");
        return true;
    }

    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder().postId(post.getId()).title(post.getTitle()).content(post.getContent()).build();
    }
}
