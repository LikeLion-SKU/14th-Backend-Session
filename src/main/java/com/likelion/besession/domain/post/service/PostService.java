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
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        System.out.println("Before update========================");
        post.updatePost(updatePostRequest.getTitle(), updatePostRequest.getContent());
        System.out.println("After update========================");
        // Post savedPost = postRepository.save(post);

        System.out.println("Before return========================");
        return toPostResponse(post);
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
