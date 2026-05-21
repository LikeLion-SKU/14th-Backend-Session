package com.example.besession.domain.post.service;

import com.example.besession.domain.post.dto.reponse.PostResponse;
import com.example.besession.domain.post.dto.request.CreatePostRequest;
import com.example.besession.domain.post.dto.request.UpdatePostRequest;
import com.example.besession.domain.post.entity.Post;
import com.example.besession.domain.post.exception.PostErrorCode;
import com.example.besession.domain.post.repository.PostRepository;
import com.example.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        // 1. DTO로부터 게시글 객체 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 2. DB에 저장
        Post savedPost = postRepository.save(post);

        // 3. postResponse 형태로 만드어서 반환
        return PostResponse.builder()
                .postId(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .build();
    }
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1() {
        // 1. List<PostResponse> 객체를 미리 생성
        List<PostResponse> postResponses = new ArrayList<>();
        // 2. DB에서 Post 목록을 불러오기
        List<Post> postList = postRepository.findAll();
        // 3. Post 목록을 PostResponse에 맞게 변환해서 변환
        for (Post post : postList) {
            postResponses.add(PostResponse.builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build());
        }
        return postResponses;
    }
    public List<PostResponse> getAllPosts2() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map( post -> PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build()).toList();

    }
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new CustomException(PostErrorCode.POST_NOT_FOUND));
        return toPostResponse(post);
    }
    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        // 1. 수정할 게시글 객체를 DB에서 불러옴
        Post post =postRepository.findById(postId).orElseThrow(()->
                new CustomException(PostErrorCode.POST_NOT_FOUND));
        // 2. 수정할 내용으로 덧붙여넣기
        post.updatePost(request);

        // 3. DB에 수정한 내용 저장
        postRepository.save(post); //이때 save 메소드를 사용하면 안되는 이유 -> 수정을 하고 나서
        //저장을 안해도 됨

        // 4. PostResponse 형태로 변환해서 반환하기
        return toPostResponse(post);
    }
    @Transactional
    public Boolean deletePost(Long postId) {
        // 1. postId로 DB에 존재하는 객체 삭제하기
        Post post  = postRepository.findById(postId).orElseThrow(()-> new CustomException(PostErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
        return true;
    }
    private PostResponse  toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }


}
