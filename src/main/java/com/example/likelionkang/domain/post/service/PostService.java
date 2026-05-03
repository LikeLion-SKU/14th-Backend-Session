package com.example.likelionkang.domain.post.service;

import com.example.likelionkang.domain.post.dto.request.CreatePostRequest;
import com.example.likelionkang.domain.post.dto.request.UpdatePostRequest;
import com.example.likelionkang.domain.post.dto.response.PostResponse;
import com.example.likelionkang.domain.post.entity.Post;
import com.example.likelionkang.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1() {
        // 1. List 객체를 미리 생성
        List<PostResponse> postResponses = new ArrayList<>();
        // 2. DB에서 Post 목록을 불러오기
        List<Post> postList = postRepository.findAll();
        // 3. Post 목록을 PostResponse에 맞게 변환해서 반환
        for (Post post : postList) {
            postResponses.add(PostResponse.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build());

        }
        return postResponses;
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2(){
        return postRepository.findAll()
                .stream().map(this::toPostResponse)
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
