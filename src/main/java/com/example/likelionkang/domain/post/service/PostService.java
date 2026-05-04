package com.example.likelionkang.domain.post.service;

import com.example.likelionkang.domain.post.dto.request.CreatePostRequest;
import com.example.likelionkang.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionkang.domain.post.dto.response.PostResponse;
import com.example.likelionkang.domain.post.entity.Post;
import com.example.likelionkang.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @org.springframework.transaction.annotation.Transactional
    public PostResponse createPost(CreatePostRequest request) {
        // 1. 게시글 객체를 통해 메서드를 만든다.
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 2. DB에 저장
        Post savedPost = postRepository.save(post);

        // 3. 반환
        return toPostResponse(savedPost);
    }
    /**
     * 게시글 전체 조회 (정렬 기능 추가)
     * @param sortBy "latest" (최신순) 또는 "views" (조회순)
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(String sortBy) {
        Sort sort;

        if ("views".equals(sortBy)) {
            // 조회수 많은 순 (내림차순)
            sort = Sort.by(Sort.Direction.DESC, "viewCount");
        } else {
            // 최신순 (기본값, ID 또는 생성일 기준 내림차순)
            sort = Sort.by(Sort.Direction.DESC, "id");
        }

        return postRepository.findAll(sort)
                .stream()
                .map(this::toPostResponse)
                .toList();
    }




    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                    new IllegalArgumentException("post Not Found"));

            return toPostResponse(post);
            //command + option + v
        }
    @org.springframework.transaction.annotation.Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        // 1. 수정할 객체를 db에서 불러온다.
       Post post = postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("post Not Found"));

        // 2. 수정할 내용으로 바꾸기
        post.updatePost(request);

        // 3. db에 저장
        postRepository.save(post);

        return toPostResponse(post);
    }
    @org.springframework.transaction.annotation.Transactional
    public Boolean deletePost(Long postId) {
        // 1.postId로 db에 존재하는 객체 삭제하기
        postRepository.deleteById(postId);
        return true;
    }

    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
