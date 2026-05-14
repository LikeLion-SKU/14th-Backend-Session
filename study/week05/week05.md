# Week 05. Repository, DTO, Service, JPA 심화

## 1. 수업 내용

이번 주차에서는 Spring Boot 백엔드 구조에서 Repository, DTO, Service 계층을 중심으로 학습하였다.

주요 내용은 다음과 같다.

- Repository & JPA 기본
- DTO
- Service와 트랜잭션
- Controller 코드 변경
- JPA 심화
- 영속성 컨텍스트

---

## 2. Repository

### Repository란?

Repository는 **Database와 통신하는 계층**이다.

Spring Data JPA의 `JpaRepository`를 상속하면 기본적인 CRUD 기능을 직접 구현하지 않아도 사용할 수 있다.

### Repository의 역할

- Entity를 DB에 저장
- Entity 조회
- Entity 수정
- Entity 삭제
- JPA 쿼리 메서드 사용
- 사용자 정의 쿼리 작성

---

## 3. JPA

### JPA란?

JPA는 Java에서 ORM 기술의 표준으로 사용되는 인터페이스 모음이다.

개발자는 JPA 규칙에 맞게 메서드명만 작성해도 DB에 접근할 수 있다.

JPA의 대표 구현체는 **Hibernate**이다.

---

## 4. ORM

### ORM이란?

ORM은 **Object Relational Mapping**의 약자이다.

객체와 관계형 데이터베이스를 연결해주는 기술이다.

ORM을 사용하면 SQL 쿼리문을 직접 작성하지 않아도 객체를 통해 DB와 상호작용할 수 있다.

### ORM의 장점

- SQL 반복 작성 감소
- 객체 중심 개발 가능
- CRUD 기능을 편리하게 사용 가능
- 유지보수 편리

---

## 5. JDBC와 JPA

### JDBC

JDBC는 Java 애플리케이션이 데이터베이스와 통신하기 위한 API이다.

특징은 다음과 같다.

- 개발자가 직접 SQL 작성
- SQL 제어와 성능 최적화에 유리
- 반복 코드가 많음
- 유지보수가 어려울 수 있음

### JPA

JPA는 ORM 기술의 표준 인터페이스이다.

특징은 다음과 같다.

- 객체(Entity)를 통해 데이터 조작
- CRUD 자동화
- 복잡한 매핑에 유리
- JPQL 사용
- 유지보수가 쉬움

### JDBC와 JPA의 관계

JPA와 JDBC는 완전히 다른 것이 아니다.

JPA도 내부적으로는 JDBC를 통해 데이터베이스와 통신한다.

---

## 6. JPQL

### JPQL이란?

JPQL은 JPA에서 사용하는 객체지향 쿼리 언어이다.

SQL은 테이블명과 컬럼명을 기준으로 작성하지만, JPQL은 엔티티명과 필드명을 기준으로 작성한다.

---

## 7. 사용자 정의 쿼리

### 7-1. 쿼리 메서드 방식

JPA는 메서드 이름을 분석하여 자동으로 쿼리를 생성한다.

메서드 이름만으로 조건 조회가 가능하다. (예: `findByTitleAndWriter`, `findTop2ByTitle`)

### 7-2. @Query 방식

`@Query`는 메서드 위에 직접 JPQL을 작성할 때 사용한다.

주의할 점은 SQL이 아니라 **JPQL**을 작성해야 한다는 것이다.

---

## 8. DTO

### DTO란?

DTO는 **Data Transfer Object**의 약자이다.

계층 간 데이터 전달을 위해 사용하는 객체이다.

도메인 Entity를 직접 주고받는 대신 DTO를 사용하면 계층 간 역할을 분리할 수 있다.

### DTO를 사용하는 이유

- Entity 직접 노출 방지
- 요청 데이터와 응답 데이터 분리
- 계층 간 데이터 전달 용도 명확화
- 유지보수성 향상

---

## 9. DTO 종류

### CreatePostRequest

게시글 생성 요청을 받을 때 사용하는 DTO로, title과 content 필드를 가진다.

### UpdatePostRequest

게시글 수정 요청을 받을 때 사용하는 DTO로, title과 content 필드를 가진다.

### PostResponse

게시글 응답을 반환할 때 사용하는 DTO로, postId, title, content 필드를 가진다.

---

## 10. @Builder

`@Builder`는 객체 생성 시 Builder 패턴을 사용할 수 있게 해주는 Lombok 어노테이션이다.

### 장점

- 생성자보다 직관적
- 매개변수 순서에 영향을 받지 않음
- 필요한 값만 선택적으로 설정 가능

Request DTO에는 테스트용을 제외하고는 굳이 사용할 필요가 없다.

---

## 11. Service

### Service란?

Service는 비즈니스 로직을 담당하는 계층이다.

Controller는 Service의 메서드를 호출하고, Service는 Repository의 메서드를 호출한다.

### Service의 역할

- 비즈니스 로직 처리
- Repository 호출
- DTO와 Entity 변환
- 트랜잭션 관리
- 예외 처리

---

## 12. JPA 기본 메서드

Spring Data JPA에서 자주 사용하는 기본 메서드는 다음과 같다.

| 메서드 | 설명 |
|---|---|
| `save(entity)` | 엔티티를 DB에 저장 |
| `findById(id)` | ID로 엔티티 조회 |
| `findAll()` | 모든 엔티티 조회 |
| `delete(entity)` | 엔티티 삭제 |
| `deleteById(id)` | ID로 엔티티 삭제 |
| `saveAll(entities)` | 여러 엔티티를 한 번에 저장 |

---

## 13. @Service

`@Service`는 해당 클래스가 Service 계층의 클래스임을 나타내는 어노테이션이다.

내부적으로 `@Component`를 포함하고 있어 Spring Bean으로 등록된다.

---

## 14. @Transactional

`@Transactional`은 트랜잭션을 적용하기 위한 어노테이션이다.

데이터베이스 작업이 모두 성공하면 Commit되고, 중간에 오류가 발생하면 Rollback된다.

### 주의사항

- public 메서드에 적용된다.
- 같은 객체 내부에서 호출하면 적용되지 않을 수 있다.
- Spring AOP 기반으로 동작한다.

---

## 15. 트랜잭션

### 트랜잭션이란?

트랜잭션은 데이터 처리 작업을 하나의 단위로 묶는 것이다.

모든 작업이 성공하면 결과를 반영하고, 하나라도 실패하면 모든 작업을 원래 상태로 되돌린다.

### 예시

계좌 이체는 출금과 입금 과정으로 이루어진다.

- 출금 성공
- 입금 실패

위와 같은 상황이 발생하면 데이터가 일관되지 않게 된다.

따라서 출금과 입금은 반드시 동시에 성공하거나 동시에 실패해야 한다.

---

## 16. 트랜잭션의 ACID 특성

트랜잭션은 ACID 특성을 가진다.

| 특성 | 의미 | 예시 |
|---|---|---|
| Atomicity | 원자성 | 출금과 입금은 모두 성공하거나 모두 실패해야 함 |
| Consistency | 일관성 | 트랜잭션 후 데이터는 일관된 상태여야 함 |
| Isolation | 격리성 | 동시에 실행되는 트랜잭션이 서로 영향을 주면 안 됨 |
| Durability | 지속성 | 완료된 결과는 영구적으로 저장되어야 함 |

---

## 17. 트랜잭션 선언 위치

### 클래스 단위 선언

클래스 위에 `@Transactional`을 붙이면 해당 클래스의 모든 public 메서드에 트랜잭션이 적용된다.

### 메서드 단위 선언

특정 메서드에만 트랜잭션을 적용할 수 있다.

---

## 18. Controller 코드 변경

Controller에서는 직접 Repository를 호출하지 않고 Service를 호출하도록 변경한다.

- 게시글 생성: `POST /posts` → Service의 createPost 호출 → 201 Created 반환
- 게시글 조회: `GET /posts/{postId}` → Service의 getPostById 호출 → 200 OK 반환
- 게시글 수정: `PUT /posts/{postId}` → Service의 updatePost 호출 → 200 OK 반환
- 게시글 삭제: `DELETE /posts/{postId}` → Service의 deletePost 호출 → 200 OK 반환

---

## 19. Controller 관련 어노테이션

### @RequestBody

HTTP 요청 Body의 JSON 데이터를 Java 객체로 변환한다.

### @PathVariable

URL 경로에 포함된 값을 메서드 파라미터로 바인딩한다.

### @Parameter

Swagger에서 요청 파라미터의 설명을 작성할 때 사용하는 어노테이션이다.

로직에는 영향을 주지 않는다.

---

## 20. API 확인

API 테스트에는 Swagger를 활용할 수 있다.

Swagger를 사용하면 브라우저에서 API 목록을 확인하고 직접 요청을 보내 테스트할 수 있다.

---

## 21. ddl-auto 설정

`application.yml`의 JPA 설정에서 `ddl-auto: update`를 사용하면 `@Entity`가 붙은 클래스의 변경 사항을 DB 테이블에 자동으로 반영한다.

---

## 22. 영속성 컨텍스트

### 영속성 컨텍스트란?

영속성 컨텍스트는 "엔티티를 영구 저장하는 환경"이라는 뜻이다.

눈에 보이지 않는 개념이지만 JPA에서 매우 중요한 역할을 한다.

JPA에서 중요한 두 가지는 다음과 같다.

- 객체와 관계형 DB 매핑
- 영속성 컨텍스트

---

## 23. 엔티티의 생명주기

Entity는 다음과 같은 상태를 가진다.

| 상태 | 설명 |
|---|---|
| 비영속 상태 | 영속성 컨텍스트와 관계없는 새로 생성된 상태 |
| 영속 상태 | 영속성 컨텍스트에서 관리되는 상태 |
| 준영속 상태 | 영속성 컨텍스트에 저장되었다가 분리된 상태 |
| 삭제 상태 | 삭제된 상태 |

---

## 24. 영속성 컨텍스트의 특징

영속성 컨텍스트의 주요 특징은 다음과 같다.

- 1차 캐시
- 트랜잭션을 지원하는 쓰기 지연
- 변경 감지
- 동일성 보장

---

## 25. 1차 캐시

영속성 컨텍스트는 내부에 1차 캐시를 가지고 있다.

한 번 조회한 Entity는 1차 캐시에 저장된다.

이후 같은 Entity를 다시 조회하면 DB에 쿼리를 보내지 않고 1차 캐시에서 가져온다.

두 번째 조회에서는 DB 쿼리가 발생하지 않을 수 있다.

이유는 첫 번째 조회 결과가 영속성 컨텍스트의 1차 캐시에 저장되어 있기 때문이다.

---

## 26. 쓰기 지연

쓰기 지연은 SQL을 바로 DB에 보내지 않고, 영속성 컨텍스트의 SQL 저장소에 모아두었다가 트랜잭션 commit 시점에 실행하는 것이다.

`save()`를 호출해도 즉시 insert 쿼리가 실행되지 않을 수 있다.

트랜잭션이 commit될 때 flush가 발생하면서 DB에 반영된다.

---

## 27. 변경 감지

변경 감지는 Dirty Checking이라고도 한다.

영속 상태의 Entity 값이 변경되면 JPA가 이를 감지하여 update 쿼리를 자동으로 생성한다.

별도의 `save()` 호출이 없어도 트랜잭션 종료 시점에 변경 내용이 DB에 반영된다.

---

## 28. Flush

### Flush란?

Flush는 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영하는 것이다.

트랜잭션이 commit되는 시점에 flush가 내부적으로 발생한다.

### 주의할 점

Flush는 영속성 컨텍스트를 비우는 것이 아니다.

Flush는 영속성 컨텍스트와 DB를 동기화하는 것이다.

---

## 29. 쿼리문 확인 방법

실행되는 SQL 쿼리를 확인하려면 `SPRING_JPA_SHOW_SQL=true` 환경 변수를 추가할 수 있다.
