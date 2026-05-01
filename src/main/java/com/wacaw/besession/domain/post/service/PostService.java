package com.wacaw.besession.domain.post.service;

import com.wacaw.besession.domain.post.dto.request.CreatePostRequest;
import com.wacaw.besession.domain.post.dto.request.UpdatePostRequest;
import com.wacaw.besession.domain.post.dto.response.PostResponse;
import com.wacaw.besession.domain.post.entity.Post;
import com.wacaw.besession.domain.post.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    // 3. PostResponse 형태로 만들어서 반환
    return toPostResponse(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts1() {
    // 1. List<PostResponse> 객체를 미리 생성
    List<PostResponse> postResponses = new ArrayList<>();

    // 2. DB에서 Post 목록을 미리 불러오기
    List<Post> postList = postRepository.findAll();

    // 3. Post 목록을 PostResponse에 맞게 변환해서 반환
    for (Post post : postList) {
      postResponses.add(toPostResponse(post));
    }
    return postResponses;
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts2() {
    List<Post> postList = postRepository.findAll();
    return postList.stream().map(post -> toPostResponse(post)).toList();
  }

  // 최신순
  @Transactional(readOnly = true)
  public List<PostResponse> getAllPostsRecent() {
    List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
    return postList.stream().map(post -> toPostResponse(post)).toList();
  }

  // 조회수순
  @Transactional(readOnly = true)
  public List<PostResponse> getAllPostsMostView() {
    List<Post> postList = postRepository.findAllByOrderByViewsDesc();
    return postList.stream().map(post -> toPostResponse(post)).toList();
  }

  @Transactional(readOnly = true)
  public PostResponse getPostById(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() ->
        new IllegalArgumentException("post Not Found"));
    return toPostResponse(post);
  }

  @Transactional
  public PostResponse updatePost(Long postId, UpdatePostRequest request) {
    // 1. 수정할 게시글 객체 DB에서 불러옴
    Post post = postRepository.findById(postId).orElseThrow();
    // 2. 수정할 내용으로 바꾸기
    post.updatePost(request);
    // 3. DB에 수정한 내용 저장 -> 필요 없음 why?
    postRepository.save(post);
    // 4. postResponse 형태로 변환해서 반환하기
    return toPostResponse(post);
  }

  @Transactional
  public Boolean deletePost(Long postId) {
    // 1. postId로 DB에 존재하는 객체 삭제하기
    postRepository.deleteById(postId);
    return true;
  }

  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .views(String.valueOf(post.getViews()))
        .build();
  }
}
