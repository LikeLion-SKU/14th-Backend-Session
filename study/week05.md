→ 전체적인 흐름 잡기

```
[ 클라잉언트 ] <--> [ 컨트롤러 ] <--> [ 서비스 ] <--> [ 레포지터리 ] <--> [ DB ]
```

## 🫠 Repository

---

DB와 통신하는 계층으로, JPA를 상속받아 CRUD 동작이 가능하다

- 🤔 JPA → 쿼리 메소드 시험✨
    - 자바에서 ORM기술의 표준으로 사용되는 인터페이스 모음 (구현체 : Hibernate)
    - JPQL(: 객체지향 쿼리 언어)로 DB가 달라도, 해당 쿼리문으로 JPA가 날려준다
    - JDBC(: DB와 통신하기 위한 API) 내장
- 🤔 ORM (Object Relational Mapping)?
    - 관계형 DB와 객체를 연결해주는 기술
- 🔎 주요 JPA 쿼리 메소드 알아보기
    - save(entity) : 엔티티를 DB에 저장
    - .delete(entity) : 엔티티를 삭제 상태로 만들어 DB에서 제거
    - .findById(id) : 해당 ID를 가진 엔티티를 DB에서 반환
    - .deleteById(id) : 해당 ID를 가진 엔티티를 DB에서 삭제
    - .findAll() : 해당 엔티티 테이블의모든 데이터를 조회
    - .saveAll(entities) : 여러 엔티티를 한번에 DB에 저장

✅ Repository 실습

1. JpaRepository 상속 받기

```java
package com.wacaw.besession.domain.post.repository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
```

→ JpaRepository도 인터페이스이기에, 만들어놓은 함수를 가져다 쓸 수 있다

1. 쿼리 작성하기

사용자 정의 쿼리 방식 2가지

- 쿼리 메소드 방식 : 메소드 이름(`findByTitleAndWriter`)으로 쿼리 생성

```java
List<Post> findByTitleAndWriter(String title, String wirter);
```

- @Query방식 : `@Query` 어노테이션을 통해, JPQL쿼리를 날림

```java

@Query("SELECT p FROM Post p WHRE p.title=:title")
List<Post> findByTitle(@Param("title") Stirng title);
```

## 🫠 DTO

---

Data Transfer Object의 약자로, 데이터 전송 객체를 의미

✅ DTO 실습하기

- 게시글 생성 요청 DTO :  `CreatePostRequest`

```java

@AllArgsConstructor
@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public class CreatePostRequest {

  @Schema(description = "게시글 제목", example = "1주차 세션: 기초 GitHub 다루기")
  private String title;

  @Schema(description = "게시글 내용", example = "GitHub를 배워요")
  private String content;
}
```

- 게시글 수정 요청 DTO : `UpdatePostRequest`

```java

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

  @Schema(description = "게시글 제목", example = "5주차 세션: DTO, Service, Repository")
  private String title;

  @Schema(description = "게시글 내용", example = "DB 접근 및 비즈니스 로직 구현을 익혀요")
  private String content;
}
```

- 게시글 응답 요청 DTO : `PostResponse`

```java

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

  @Schema(description = "게시글 ID", example = "1")
  private Long postId;

  @Schema(description = "게시글 제목", example = "4주차 세션: Entity, Controller, Swagger")
  private String title;

  @Schema(description = "게시글 내용", example = "Entity, Controller, Swagger를 익혀요")
  private String content;
}
```

- 🤔 Q. 식별자가 왜 response와 request에 게시글 아이디를 왜 안담을까?
    - 조회, 수정 , 삭제는 path variable로 엔드포인트로 줌
    - 식별자에 담아가지고, 응답에 PostResponse

## 🫠 Service

---

비즈니스 로직을 담당한다

- 서비스 어노테이션
    - `@Service` : 서비스 레이어 클래스임을 나타냄 (`@Component` 포함)
    - `@Transactional` : 선언적 DB 트랜잭션 관리 방법 제공

✅ Service 실습하기

- 게시글 서비스 : `PostService`

```java
package com.wacaw.besession.domain.post.service;

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
        .build();
  }
}

```

✅ 트랜잭션이란?

데이터 거래에 있어서 안정성을 확보하기 위한 방법이다. 데이터 처리 과정에서 다음과 같이 동작한다.

- 오류 발생 시 : “모든 작업”을 원상태 복구(`Rollback` )
- 성공 시: 결과 반영 (`Commit`)

- Transaction의 특성 : ACID
    - A (Atomicity, 원자성) : 트랜잭션 내의 모든 작업은 모두 성공하거나 모두 실패
    - C (Consistency, 일관성) : 트랜잭션이 완료되면 데이터는 일관된 상태
    - I  (Isolation, 격리성) : 트랜잭션은 서로 독립적으로 실행되어야 하며, 동시에 실행되는 다른 트랜잭션이 영향을 미치지 않음
    - D (Durability, 지속성) : 트랜잭션이 완료되면 그 결과는 영구적으로 저장

- 트랜젝션 선언 위치
    - 클래스 : 해당 클래스의 모든 public 메소드에 자동 적용

    ```java
    @Transactional
    public class PostService{ }
    ```

    - 메소드 : 해당 메소드에만 적용

    ```java
    @Transactional
    public Boolean deletePost(Long id) { }
    ```

## 🫠 Controller에 적용하기

---

Comtroller 어노테이션

- @Parameter : Swagger 어노테이션, 요청 파라미터의 메타데이터 정의 (로직에 영향X)
- @RequestBody : HTTP 요청 Body(Json 등)를 Java 객체(DTO)로 매핑
- @PathVariable : URL 경로(Path)에 포함된 값을 메서드 파라미터로 바인딩

```java
package com.wacaw.besession.domain.post.controller;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Post", description = "게시물 관련 API")
public class PostController {

  private final PostService postService;

  @Operation(
      summary = "게시글 생성",
      description = "요청으로 전달된 게시글 정보로 새로운 게시글을 생성하는 API"
  )
  @PostMapping("/posts") // 반환값 :  ResponseEntity<PostResponse>
  public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request) {
    PostResponse response = postService.createPost(request); // Sercie 메서드 호출을 통해 필요한 DTO 받기
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response); // 응답 본문에 DTO 추가
  }

  @Operation(
      summary = "게시글 전체 조회",
      description = "모든 게시글 목록을 조회하는 API"
  )
  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    List<PostResponse> responses = postService.getAllPosts2();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responses);
  }

  @Operation(
      summary = "게시글 단건 조회",
      description = "게시글 ID로 특정 게시글을 조회하는 API"
  )
  @GetMapping("/posts/{post-id}")
  public ResponseEntity<PostResponse> getPostById(@PathVariable("post-id") Long postId) {
    PostResponse response = postService.getPostById(postId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @Operation(
      summary = "게시글 수정",
      description = "게시글의 ID와 요청으로 전달된 게시글 정보로 게시글을 수정하는 API"
  )
  @PutMapping("/posts/{post-id}")
  public ResponseEntity<PostResponse> updatePost(@PathVariable("post-id") Long postId,
      @RequestBody UpdatePostRequest request) {
    PostResponse response = postService.updatePost(postId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  @Operation(
      summary = "게시글 삭제",
      description = "게시글 ID로 특정 게시글을 삭제하는 API"
  )
  @DeleteMapping("/posts/{post-id}")
  public ResponseEntity<Boolean> deletePost(@PathVariable("post-id") Long postId) {
    Boolean response = postService.deletePost(postId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }
}

```

## 🫠 영속성 컨텍스트

---

"엔티티를 영구 저장하는 환경"이라는 논리적인 개념

- 특징
    - **1차 캐시**: 영속성 컨텍스트 내부에 Map 형태의 저장소를 가진다. 한 트랜잭션 내에서 같은 ID로 조회 시 DB가 아닌 캐시에서 바로 가져와 성능을 높인다
    - **동일성(Identity) 보장**: `a == b` 비교 시 true를 보장한다 (1차 캐시 덕분)
    - **트랜잭션을 지원하는 쓰기 지연 (Transactional Write-behind)**: `persist()` 호출 시 바로 INSERT SQL을 보내지 않고, 내부
      쿼리 저장소에 모아두었다가 트랜잭션 커밋 시 한꺼번에 실행한다
    - **변경 감지 (Dirty Checking)**: 엔티티 수정 시 별도의 `update()` 호출 없이, 트랜잭션 커밋 시점에 스냅샷과 비교하여 변경된 부분을 자동으로
      반영한다
    - **지연 로딩 (Lazy Loading)**: 연관된 객체를 실제로 사용하는 시점에 DB에서 조회할 수 있게 지원한다
- 엔티티의 생명주기
    - 비영속 상태 : 영속성 컨텍스트와 전혀 관계 없는 새로운 상태 (new/transient)
    - 준영속 상태 : 영속성 컨텍스트에 저장되었다가 분리된 상태 (detached)
    - 영속 상태 : 영속성 컨텍스트에서 관리되는 상태 (managed)
    - 삭제 상태 : 삭제된 상태 (removed)
- **Flush의 역할**: 영속성 컨텍스트의 변경 내용을 DB에 반영(동기화)하는 과정
    - **발생 시점**: `em.flush()` 직접 호출, 트랜잭션 커밋 시 자동 호출, JPQL 쿼리 실행 시 자동 호출
    - 영속성 컨텍스트를 비우는 것이 아니라, **쓰기 지연 SQL 저장소의 쿼리를 DB에 전송**하는것뿐
- **Commit과의 관계**:
    - `Flush`는 DB에 쿼리를 보내는 단계 (아직 롤백 가능)
    - `Commit`은 DB의 작업을 최종 확정하는 단계
    - `@Transactional`이 붙은 메서드가 종료되면 스프링이 내부적으로 `commit()`을 호출하며, 이때 `flush()`가 먼저 일어난다
