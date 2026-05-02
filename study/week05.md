# 📚 5주차 세션 정리

## 🔹 ORM (Object Relational Mapping)

- 객체(Object)와 관계형 데이터베이스(Relational Database)를 연결해주는 기술
- Java 객체를 통해 DB 데이터를 쉽게 조작 가능
- SQL을 직접 작성하지 않고 객체 중심으로 개발 가능

### ✔ ORM 장점

- SQL 코드 감소
- 생산성 향상
- 유지보수 편리
- 객체지향적인 코드 작성 가능

---

## 🔹 JPA (Java Persistence API)

- 자바에서 ORM 기술의 표준으로 사용되는 **인터페이스 모음**
- CRUD SQL을 직접 작성하지 않아도 DB와 쉽게 상호작용 가능
- 메서드 이름 규칙만 맞춰도 자동으로 쿼리 생성 가능

### ✔ JPA 구현체

- **Hibernate**
  → JPA를 실제로 구현한 대표 구현체

구조:

```text
개발자 → JPA → Hibernate → Database
```

---

## 🔹 JDBC vs JPA

### ✔ JDBC

- SQL 직접 작성
- PreparedStatement 사용
- 반복 코드 많음
- 유지보수 어려움

### ✔ JPA

- Entity 객체 기반으로 데이터 조작
- CRUD 자동화
- SQL 작성 최소화
- 유지보수 쉬움

---

## 🔹 JPQL (Java Persistence Query Language)

- JPA에서 사용하는 **객체지향 쿼리 언어**
- SQL과 문법은 비슷하지만 기준이 다름

### ✔ SQL

- 테이블명 기준 작성
- 컬럼명 기준 작성

```sql
select * from post where title = '제목';
```

### ✔ JPQL

- 엔티티명 기준 작성
- 필드명 기준 작성


```java
select p from Post p where p.title = '제목'
```

### ✔ 장점

- DB 구조가 바뀌어도 유연하게 대응 가능

---

## 🔹 사용자 정의 쿼리

### ✔ 1. 쿼리 메소드 방식

- 메서드 이름만으로 쿼리 자동 생성


```java
findByTitle(String title)
```

자동 생성되는 쿼리:

```sql
select * from post where title = ?
```

```java
findById(Long id)

findAll()

findByName(String name)

findAllByOrderByIdDesc()

findAllByOrderByViewCountDesc()
```

---

### ✔ 2. @Query 방식

- 직접 JPQL 작성


```java
@Query("select p from Post p where p.title = :title")
List<Post> findPost(String title);
```

주의:

- SQL X
- JPQL O

즉,

- 테이블명 X
- 컬럼명 X
- 엔티티명 O
- 필드명 O

---

## 🔹 DTO (Data Transfer Object)

- 계층 간 데이터를 전달하기 위한 객체
- Entity를 직접 노출하지 않기 위해 사용


### Request DTO

```java
CreatePostRequest
```

### Response DTO

```java
PostResponse
```

### ✔ 장점

- 역할 분리
- 데이터 보호
- 유지보수 편리

---

## 🔹 @Builder

- 객체 생성을 편하게 도와주는 Lombok 기능


```java
Post post = Post.builder()
        .title("제목")
        .content("내용")
        .build();
```

### ✔ 장점

- 직관적
- 매개변수 순서 상관 없음
- 가독성 좋음

---

## 🔹 Service

- 비즈니스 로직을 담당하는 계층
- 프로젝트에서 가장 핵심 로직이 들어가는 부분

구조:

```text
Controller → Service → Repository → DB
```
---

## 🔹 자주 사용하는 JPA 메서드

### ✔ save(entity)

- 엔티티 저장

```java
postRepository.save(post);
```

---

### ✔ saveAll(entities)

- 여러 엔티티 한 번에 저장

```java
postRepository.saveAll(posts);
```

---

### ✔ findById(id)

- ID로 단건 조회

```java
postRepository.findById(id);
```

---

### ✔ findAll()

- 전체 조회

```java
postRepository.findAll();
```

---

### ✔ delete(entity)

- 엔티티 삭제

```java
postRepository.delete(post);
```

---

### ✔ deleteById(id)

- ID 기준 삭제

```java
postRepository.deleteById(id);
```

---

## 🔹 @Service

- Service 계층 클래스임을 나타내는 어노테이션
- Spring Bean으로 자동 등록됨

```java
@Service
public class PostService {
}
```

---

## 🔹 @Transactional

- 선언적 데이터베이스 트랜잭션 관리 제공

```java
@Transactional
public void createPost() {
}
```

### ✔ 역할

정상 종료:

→ Commit

오류 발생:

→ Rollback

### ✔ 특징

- public 메서드에만 적용
- 같은 객체 내부 호출 시 적용 안 됨

---

## 🔹 Transaction (트랜잭션)

- 데이터 처리의 **하나의 작업 단위**


회원가입 처리

1. 회원 저장
2. 포인트 저장
3. 로그 저장

중간 실패 시:

→ 전체 취소 (Rollback)

전부 성공 시:

→ Commit

---

## 🔹 Transaction 특징 - ACID

### ✔ Atomicity (원자성)

- 전부 성공하거나 전부 실패해야 함

### ✔ Consistency (일관성)

- 데이터는 항상 올바른 상태 유지

### ✔ Isolation (격리성)

- 동시에 실행되는 트랜잭션끼리 간섭하지 않음

### ✔ Durability (지속성)

- Commit된 데이터는 영구 저장됨

---

## 🔹 영속성 컨텍스트

- 엔티티를 영구 저장하고 관리하는 JPA 내부 공간
- 눈에 보이지 않는 개념적인 저장소

JPA 핵심:

1. 객체와 DB 매핑
2. 영속성 컨텍스트 관리

---

## 🔹 엔티티 생명주기

### ✔ 비영속 상태 (new / transient)

- 새로 생성된 객체
- JPA가 관리하지 않음

```java
Post post = new Post();
```

---

### ✔ 영속 상태 (managed)

- 영속성 컨텍스트에서 관리 중인 상태

```java
postRepository.save(post);
```

---

### ✔ 준영속 상태 (detached)

- 관리되다가 분리된 상태

---

### ✔ 삭제 상태 (removed)

- 삭제 예정 상태

---

## 🔹 영속성 컨텍스트 특징

### ✔ 1차 캐시

- 메모리에 객체 저장
- 같은 데이터 재조회 시 DB 접근 감소

---

### ✔ 쓰기 지연

- SQL을 바로 보내지 않고 모아뒀다가 한 번에 실행

---

### ✔ 변경 감지 (Dirty Checking)

- 객체 값이 바뀌면 자동으로 update SQL 생성

```java
post.updatePost(request);
```

---

### ✔ 동일성 보장

- 같은 엔티티는 같은 객체로 관리

---

## 🔹 Flush

- 영속성 컨텍스트의 변경 내용을 DB에 반영하는 작업

발생 시점:

- commit 시 자동 실행

흐름:

```text
객체 수정
↓
변경 감지
↓
flush
↓
update SQL 실행
↓
commit
```

---

## 🔹 save() 없이 수정되는 이유

- JPA의 **Dirty Checking** 때문

```java
Post post = postRepository.findById(id).get();

post.updatePost(request);
```
```text
save 없음
↓
트랜잭션 종료
↓
flush 발생
↓
자동 update SQL 실행
```