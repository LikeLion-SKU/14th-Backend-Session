package com.likelion.besession.domain.post.service;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.exception.PostErrorCode;
import com.likelion.besession.domain.post.repository.PostRepository;
import com.likelion.besession.global.exception.CustomException;
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
    public PostResponse creatPost(CreatePostRequest request){
        // 1. DTO로부터 게시글 객체 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        // 2. DB에 저장
        Post savedPost = postRepository.save(post);
        // 3. PostResponse 형태로 만들어서 반환
        return toPostResponse(savedPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1(){
        // 1. List<PostResponse> 객체를 미리 생성
        List<PostResponse> postResponses = new ArrayList<>();

        // 2. DB에서 Post 목록을 불러오기
        List<Post> postList = postRepository.findAll();

        // 3. Post 목록을 PostResponse에 맞게 변환해서 반환
        for(Post post : postList){
            postResponses.add(toPostResponse(post));
        }
        return  postResponses;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2(){
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    // 조회수 증가(상태 변경)가 일어나므로 readOnly = true는 제외하되, 예외 처리를 교안 구조로 변경합니다.
    @Transactional
    public PostResponse getPostById(Long postId){
        // IllegalArgumentException 대신 커스텀 예외인 CustomException을 던지도록 수정합니다.
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(PostErrorCode.POST_NOT_FOUND));
        post.increaseViews();

        return toPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getLatestPosts(){
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getBestPosts(){
        List<Post> postList = postRepository.findAllByOrderByViewsDesc();
        return postList.stream().map(post -> toPostResponse(post)).toList();
    }

    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request){
        // 1. 수정할 게시글 객체를 DB에서 불러옴 (커스텀 예외 적용)
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(PostErrorCode.POST_NOT_FOUND));

        // 2. 수정할 내용으로 바꾸기
        post.updatePost(request);

        // DB에 수정한 내용 저장 (JPA 영속성 컨텍스트의 더티 체킹 기능 덕분에 사실 save 호출은 생략해도 무방합니다.)
        postRepository.save(post);

        // 3. PostResponse 형태로 변환해서 반환하기
        return toPostResponse(post);
    }

    @Transactional
    public Boolean deletePost(Long postId){
        // 교안 실습 내용에 맞춰, 존재하지 않는 postId를 삭제하려 할 때 예외를 발생시키도록 조회 단계를 추가합니다.
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new CustomException(PostErrorCode.POST_NOT_FOUND)); //

        // 1. 불러온 엔티티 객체를 이용해 DB에서 삭제하기
        postRepository.delete(post); //
        return true;
    }


    private PostResponse toPostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .views(post.getViewCount())
                .build();
    }
}