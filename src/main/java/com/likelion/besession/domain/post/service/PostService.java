package com.likelion.besession.domain.post.service;

import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.entity.PostFilter;
import com.likelion.besession.domain.post.exception.PostErrorCode;
import com.likelion.besession.domain.post.repository.PostRepository;
import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.global.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service // Component Scan 후 빈으로 등록
@RequiredArgsConstructor


public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest request){
        // 1. DTO로부터 게시글 객체 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 2. DB에 저장
        Post savedPost = postRepository.save(post);
        // 3. PostResponse 형태로 만들어서 반환
        return toPostResponse(post);

    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1(){
        // 1. List<PostResponse> 객체를 미리 생성
        List<PostResponse> postResponses = new ArrayList<>();
        // 2. DB에서 Post 목록을 불러오기
        List<Post> postList = postRepository.findAll();
        // 3. Post 목록을 PostReponse에 맞게 변환해서 반환
        for (Post post : postList){
            postResponses.add(toPostResponse(post));
        }
        return postResponses;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllposts2(PostFilter filter){
        List<Post> posts;

        if (filter == PostFilter.LATEST) {
            posts = postRepository.findAllByOrderByIdDesc();
        } else if (filter == PostFilter.VIEW_COUNT) {
            posts = postRepository.findAllByOrderByViewCountDesc();
        } else {
            posts = postRepository.findAll();
        }

        return posts.stream()
                .map(this::toPostResponse)
                .toList();
    }
    @Transactional
    public PostResponse getPostById(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();
        return toPostResponse(post);
    }
    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request){
        // 1. 수정할 게시글 객체를 DB에서 불러옴.
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));
        // 2. 수정할 내용으로 바꾸기
        post.updatePost(request);
        // ?. DB에 수정한 내용 저장 (없어도 된다)
        postRepository.save(post);

        // 3. PostResponse 형태로 변환해서 반환하기
        return toPostResponse(post);
    }
    @Transactional
    public Boolean deletePost(Long postId){
        // 1. postId로 DB에 존재하는 객체 찾기 (없으면 예외 발생)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(PostErrorCode.POST_NOT_FOUND));

        // 2. 찾아온 post 객체 자체를 넘겨서 삭제하기
        postRepository.delete(post);

        return true;
    }


    private PostResponse toPostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .build();
    }
}
