package com.likelion.besession.domain.post.service;

import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.entity.PostSortType;
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
        // 게시물 객체 생성 -> Builder로 만들기
        Post post = Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
//                .viewCount(0L) // 조회수는 0으로 시작해야함 -> 빌더 디폴트 세팅 -> 조회수 0
                .build();

        // 리포지토리를 이용하여 DB에 저장 -> 저장된 객체 resultPost에 가져오기
        Post resultPost = postRepository.save(post);

        // 저장 완료하였으면, 저장 결과(resultPost) 반환
        return toPostResponse(resultPost);
    }

    // 모든 게시물 불러오기 - 1번 방식
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts1(){

        // Repository를 이용하여 객체 리스트 불러오기
        List<Post> posts = postRepository.findAll();

        // 반환 결과를 저장할 PostResponse 리스트를 만들기
        List<PostResponse> postResponses = new ArrayList<>();

        // 객체 리스트 for문을 돌며, PostResponse로 변환
        for(Post post : posts){
            PostResponse tempPostResponse = toPostResponse(post);

            postResponses.add(tempPostResponse);
        }

        return postResponses;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts2(){
        List<Post> posts = postRepository.findAll();

        List<PostResponse> postResponseList =
                posts.stream().map(post -> toPostResponse(post)).toList();

        return postResponseList;
    }

    // Post 객체 -> PostResponse DTO 변환
    private PostResponse toPostResponse(Post post){
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .createdDate(post.getCreatedDate())
                .build();
    }

    // 단건 조회(readOnly 값 false 지정 X -> 조회수 증가해야함)
    @Transactional
    public PostResponse getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post Not Found"));
        // post.setViewCount(post.getViewCount() + 1); // 조회수 1 증가
        post.viewPost(); // 조회수 1 증가 -> 엔티티 내부 메소드로 증가

        PostResponse postResponse = toPostResponse(post);
        return postResponse;
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post Not Found"));

        post.updatePost(updatePostRequest);

        // 영속 엔티티이므로, 메소드 종료시 flush 됨 -> save 불필요
         // Post updatedPost = postRepository.save(post);

        return toPostResponse(post);
    }

    @Transactional
    public Boolean deletePost(Long id) {
        postRepository.deleteById(id);

        return true;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPostsWithSortType(PostSortType sortType){

        // DB Post 테이블에서 데이터 가져오기
        List<Post> posts;

        // 반환 할 PostResponse 리스트 선언
        List<PostResponse> postResponses = new ArrayList<>();

        // sortType에 맞춰 알맞은 쿼리 호출
        switch (sortType){
            case sortType.createdDate:
                posts = postRepository.findAllByOrderByCreatedDateDesc();
                break;
            case sortType.viewCount:
                posts = postRepository.findAllByOrderByViewCountDesc();
                break;
            default:
                posts = postRepository.findAll();
                break;
        }

        // Post 객체 -> PostResponse 변환 / 이후 리스트에 넣기
        for(Post post : posts){
            postResponses.add(toPostResponse(post));
        }

        return postResponses;
    }


}