package com.example.likelionbe.domain.post.service;

import com.example.likelionbe.domain.post.dto.request.CreatePostRequest;
import com.example.likelionbe.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionbe.domain.post.dto.response.PostResponse;
import com.example.likelionbe.domain.post.entity.Post;
import com.example.likelionbe.domain.post.entity.PostFilter;
import com.example.likelionbe.domain.post.repository.PostRepository;
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
        return toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(UpdatePostRequest request) {
        Post post = postRepository.findById(request.postId()).orElseThrow(() -> new RuntimeException("post not found"));
        post.updatePost(request.title(), request.content());
        return toPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostList(PostFilter filter) {
        List<Post> posts;
        if(filter==null) {
            posts = postRepository.findAll();
            return posts.stream().map(this::toPostResponse).toList();
        }

        if(filter.equals(PostFilter.LATEST)){
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        } else {
            posts = postRepository.findAllByOrderByViewCountDesc();
        }
        return posts.stream().map(this::toPostResponse).toList();
    }

    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found"));
        post.viewPost();
        return toPostResponse(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
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
