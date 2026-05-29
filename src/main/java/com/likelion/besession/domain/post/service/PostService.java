package com.likelion.besession.domain.post.service;


import com.likelion.besession.domain.post.dto.request.CreatePostRequest;
import com.likelion.besession.domain.post.dto.request.UpdatePostRequest;
import com.likelion.besession.domain.post.dto.response.PostResponse;
import com.likelion.besession.domain.post.entity.Post;
import com.likelion.besession.domain.post.exception.PostErrorCode;
import com.likelion.besession.domain.post.repository.PostRepository;
import com.likelion.besession.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public PostResponse createPost(CreatePostRequest request) {
    log.info("게시글 생성 Request: {}", request);
    //1. 빌더로 post 객체 생성하기
    Post post = Post.builder()
        .title(request.getTitle())
        .content(request.getContent())
        .build();

    log.info("Create post: {}", post);
    //2. postRepository에 새로 만든 post 객체 담아서 save하기
    Post savedPost = postRepository.save(post);

    //3. 만든 객체 응답 dto에 담아서 리턴하기
    PostResponse response = toPostResponse(savedPost);

    return response;
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts1() {
    //1. List<PostResponse> 객체 미리 생성
    List<PostResponse> postResponses = new ArrayList<>();

    //2. DB에서 Post 목록 가져오기
    List<Post> postList = postRepository.findAll();
    log.debug("Get all posts {}", postList.size());

    //3. Post 목록을 PostResponse에 맞게 변환해서 반환
    for(Post post : postList){
      postResponses.add(toPostResponse(post));
    }
    return postResponses;
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts2() {
    List<Post> postList = postRepository.findAll();

    log.debug("게시글 전체 조회 완료 - 총 {}", postList.size());
    return postList.stream().map(post -> toPostResponse(post)).toList();
  }

  @Transactional
  public PostResponse getPostById(Long id) {
    log.info("게시글 단건 조회 - PostId: {}", id);
    //1. DB에서 해당 id를 가진 Post가져오기
    Post post = postRepository.findById(id).orElseThrow(() ->{
      log.warn("게시글을 찾을 수 없습니다 - postId: {}", id);
      return new CustomException(PostErrorCode.POST_NOT_FOUND);
    });

    log.info("게시글 단건 조회 - Post : {}", post);
    //2. 조회수 증가
    post.increaseViewCount();

    //Response형태로 리턴하기
    return toPostResponse(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsSortedByLatest() {
    return postRepository.findAllByOrderByCreatedAtDesc()
        .stream().map(this::toPostResponse).toList();
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsSortedByViewCount() {
    return postRepository.findAllByOrderByViewCountDesc()
        .stream().map(this::toPostResponse).toList();
  }

  @Transactional
  public PostResponse updatePost(Long postId, UpdatePostRequest request) {
    log.info("게시글 수정 Post Id : {}", postId);
    //1.수정할 게시글 객체 DB에서 불러오기
    Post post = postRepository.findById(postId).orElseThrow(() ->{
      log.warn("게시글을 찾을 수 없습니다 - postId: {}", postId);
      return new CustomException(PostErrorCode.POST_NOT_FOUND);
    });

    //2.엔티티 직접 들어가서 Post객체 수정하기 -> 단지 영속성컨텍스트 안의 엔티티를 수정하기만 하면 그 변경사항을 DB에 자동 반영해줌
    post.updatePost(request);

    log.info("게시글 수정 완료: {}, title: {}", post.getId(), post.getTitle());
    //?. DB에 수정한 내용 저장 X
    //postRepository.save(post);  -> **영속성 컨텍스트**에 저장하는 행위(필요없음!)

    //3.postResponse형태로 변환해서 반환하기
    return toPostResponse(post);
  }

  @Transactional
  public Boolean deletePost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() ->
        new CustomException(PostErrorCode.POST_NOT_FOUND));

    postRepository.delete(post);

    return true;
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
