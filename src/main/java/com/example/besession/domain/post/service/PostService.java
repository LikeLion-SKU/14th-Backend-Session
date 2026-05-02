package com.example.besession.domain.post.service;

import com.example.besession.domain.post.dto.request.CreatePostRequest;
import com.example.besession.domain.post.dto.request.UpdatePostRequest;
import com.example.besession.domain.post.dto.response.PostResponse;
import com.example.besession.domain.post.entity.Post;
import com.example.besession.domain.post.respository.PostRepository;
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
    // 게시글 생성
    public PostResponse createPost(CreatePostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        return toPostResponse(post);
    }

    @Transactional(readOnly = true)
    // 게시글 전체 조회 - for문 방식
    public List<PostResponse> getAllPosts1() {
        List<PostResponse> postResponses = new ArrayList<>();

        List<Post> postList = postRepository.findAll();

        for (Post post : postList) {
            postResponses.add(toPostResponse(post));
        }

        return postResponses;
    }

    @Transactional(readOnly = true)
    // 게시글 전체 조회 - stream 방식
    public List<PostResponse> getAllPosts2() {
        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(post -> toPostResponse(post))
                .toList();
    }

    @Transactional // readOnly=true 제거
    //게시글 단일 조회
    public PostResponse findById(Long PostId) {
        Post post = postRepository.findById(PostId).orElseThrow(() -> new IllegalArgumentException("post Not Found"));
        //조회수 증가
        post.increaseViewCount();
        return toPostResponse(post);
    }

    @Transactional
    //게시글 수정
    public PostResponse updatePost(Long postId,UpdatePostRequest request) {
        // 1. 수정할 게시글 객체를 DB에서 불러옴
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("post Not Found"));

        // 2. 수정할 내용을 바꾸기
        post.updatePost(request);

        // ? DB에 수정한 내용 저장을 수정에서는 하면 안됨!!
        //postRepository.save(post);

        // 3. PostResponse 형태로 변환해서 반환하기.
        return toPostResponse(post);

    }

    @Transactional
    public Boolean deletePost(Long postId) {
        // 1. postId로 DB에 존재하는 객체 삭제하기
          postRepository.deleteById(postId);

          // 2. 성공/실패 반환
          return true;
    }

    // 최신순
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsLatest() {
        return postRepository.findAllByOrderByIdDesc()
                .stream()
                .map(this::toPostResponse)
                .toList();
    }

    //조회순
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsPopular() {
        return postRepository.findAllByOrderByViewCountDesc()
                .stream()
                .map(this::toPostResponse)
                .toList();
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