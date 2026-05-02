# 📚 Spring Boot 계층 및 JPA 정리

---

## 🗂️ Repository

- Database와 통신하는 계층
- JPA를 상속받음으로써 기본적인 CRUD 동작이 가능

---

## ☕ JPA

- 자바에서 ORM 기술의 표준으로 사용되는 인터페이스 모음
    - ORM - SQL 쿼리문을 작성하지 않아도 쉽게 DB와 상호작용 하도록 도와줌
- Entity를 통해 데이터 조작
- 복잡한 매핑 작업에 유리
- JPQL 사용
- 유지보수 쉬움, CRUD 자동화
- 영속성 컨텍스트

---

## 📝 JPQL

- JPA에서 사용하는 객체지향 쿼리 언어
- DB 구조가 바뀌어도 유연하다.

---

## 🔍 사용자 정의 쿼리

### 1️⃣ 메소드 이름으로 쿼리를 생성함

```java
List<Post> findTop@ByTitle(String title);
```

### 2️⃣ @Query를 사용하여 직접 쿼리 작성

```java
@Query("SELECT p FROM Post p WHERE p.title = :title")
List<Post> findByTitle(@Param("title")String title);
```

---

## 📦 DTO

- data transfer object의 약자로 데이터 전송 객체를 의미 계층 간 데이터 전송을 위해 도메인 모델 대신 사용되는 객체
- Builder
    - 생성자 방식보다 직과적
    - 생성자와 달리 매개변수 순서에 상관없이 생성 가능

---

## 🧩 Service

- 비즈니스 로직을 담당, 코드 복잡, controller는 service 메소드를 호출, service에서는 repository 메소드를 호출함
- JPA 쿼리 메소드
    - `.save(entity)`
    - `.findById(id)`
    - `.findAll()`
    - `.delete(entity)`
    - `.deleteById((id)`
    - `.saveAll(entities)`

---

## 🔐 트랜잭션

- 데이터 거래에 있어서 안정성을 확보하기 위한 방법
    - Atomicity - 원자성
    - Consistency - 일관성
    - Isolation - 격리성
    - Durability - 지속성

---

## 🧠 영속성 컨텍스트

- 엔티티를 영구 저장하는 환경
- 눈에 보이지 않는 개념

```text
java 프로그램 - 영속성 컨텍스트 - DB
```

### 🔄 엔티티의 생명주기

- 비영속 상태 - 영속성 컨텍트와 관계 없는 새로운 상태
- 영속 상태 - 영속성 컨텍스트에서 관리되는 상태
- 준영속 상태 - 저장되었다가 분리된 상태
- 삭제 상테 - 삭제된 상태

### ✨ 특징

- 1차 캐시
- 트랜잭션을 지원하는 쓰기 지연
- 변경 감지
- 동일성 보장

### 🚿 Flush

- 영속성 컨텍슽의 변경 내용을 데이터베이스에 반영하는 것
- 데이터베이스에 Transaction이 commit 되는 시점에 flush가 내부적 발생
- commit은 트랜잭션 어노테이션이 붙은 메소드가 종료될 때 내부적 발생